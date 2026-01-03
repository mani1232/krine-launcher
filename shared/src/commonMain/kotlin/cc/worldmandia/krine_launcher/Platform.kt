package cc.worldmandia.krine_launcher

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform