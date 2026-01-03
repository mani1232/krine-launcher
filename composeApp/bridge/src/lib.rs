pub mod models {
    include!(concat!(env!("OUT_DIR"), "/_.rs"));
}
use crate::models::{DownloadProgress, MinecraftData};
use prost::Message;
use tauri::{command, ipc::Response, AppHandle, Emitter};

#[command]
fn greet(name: &str) -> String {
    format!("Hello, {}! You've been greeted from Rust!", name)
}

#[command]
fn process_user_data(payload: Vec<u8>) -> Response {
    match MinecraftData::decode(&payload[..]) {
        Ok(mut data) => {
            println!("We need download {} for {}", data.version, data.platform);

            data.version = format!("{}-patched", data.version);
            let required_size = data.encoded_len();

            let mut new_payload = Vec::with_capacity(required_size);

            match data.encode(&mut new_payload) {
                Ok(_) => {
                    println!("Data patched successfully!");
                    Response::new(new_payload)
                }
                Err(e) => {
                    println!("Error encoding back: {}", e);
                    Response::new(Vec::new())
                }
            }
        }
        Err(message) => {
            println!("Got error: {}", message);
            Response::new(Vec::new())
        }
    }
}

#[command]
fn start_download(app: AppHandle) {
    std::thread::spawn(move || {
        for i in 0..=100 {
            let data = DownloadProgress {
                file: "minecraft_1.21.jar".to_string(),
                percent: i,
            };

            let len = data.encoded_len();
            let mut buf = Vec::with_capacity(len);
            data.encode(&mut buf).unwrap();

            app.emit("download-progress", buf).unwrap();

            std::thread::sleep(std::time::Duration::from_secs(1));
        }
    });
}

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .plugin(tauri_plugin_opener::init())
        .invoke_handler(tauri::generate_handler![
            greet,
            process_user_data,
            start_download
        ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
