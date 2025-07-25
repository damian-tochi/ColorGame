import SwiftUI
import shared


struct GameScreenView: View {
    let gameEngine: GameEngine
    @State private var state: GameState

    init(gameEngine: GameEngine) {
        self.gameEngine = gameEngine
        _state = State(initialValue: gameEngine.state)
    }

    var body: some View {
        VStack {
            Text("Avoid Color")
            Rectangle()
                .fill(Color(hex: state.highlightedColor))
                .frame(width: 80, height: 80)
            HStack {
                ForEach(state.colors, id: \ .self) { color in
                    Rectangle()
                        .fill(Color(hex: color))
                        .frame(width: 80, height: 80)
                        .onTapGesture {
                            gameEngine.onColorSelected(color: color)
                            state = gameEngine.state
                        }
                }
            }
            Text("Time: \(state.timeRemaining)")
        }
    }
}