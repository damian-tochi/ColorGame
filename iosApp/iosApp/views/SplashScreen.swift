import SwiftUI
import shared

struct SplashScreenView: View {
    let onTimeout: () -> Void
    @State private var navigate = false

    var body: some View {
        Text("Color Game")
            .onAppear {
                DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                    onTimeout()
                }
            }
    }
}