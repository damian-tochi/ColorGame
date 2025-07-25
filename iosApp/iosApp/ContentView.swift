import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()

	var body: some View {
		Text(greet)
	}
}

struct ContentView: View {
    @StateObject private var viewModel = ColorGameViewModel()

    var body: some View {
        NavigationStack {
            if viewModel.showSplash {
                SplashScreenView {
                    viewModel.startGame()
                }
            } else {
                ColorGameScreenView(gameEngine: viewModel.gameEngine)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}