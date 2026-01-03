use std::path::Path;

fn main() {
    let proto_root = Path::new("../../proto");

    let proto_file = proto_root.join("models.proto");

    if !proto_root.exists() {
        panic!("Proto folder not found at: {:?}", proto_root.canonicalize());
    }

    println!("cargo:rerun-if-changed={}", proto_file.display());

    prost_build::compile_protos(
        &[proto_file],
        &[proto_root]
    ).expect("Failed to compile Protobuf definitions");

    tauri_build::build()
}
