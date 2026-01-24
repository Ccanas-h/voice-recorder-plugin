// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "TobalVoiceRecorder",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "TobalVoiceRecorder",
            targets: ["VoiceRecorderPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "VoiceRecorderPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/VoiceRecorderPlugin"),
        .testTarget(
            name: "VoiceRecorderPluginTests",
            dependencies: ["VoiceRecorderPlugin"],
            path: "ios/Tests/VoiceRecorderPluginTests")
    ]
)