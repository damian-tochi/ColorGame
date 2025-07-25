import SwiftUI

class ColorGameViewModel: ObservableObject {
    @Published var showSplash = true
    let gameEngine = GameEngine()

    func startGame() {
        showSplash = false
    }
}
