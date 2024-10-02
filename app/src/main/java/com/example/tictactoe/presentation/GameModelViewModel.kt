package com.example.tictactoe.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GameModelViewModel @Inject constructor() : ViewModel() {

  // X -> 1
  // 0 -> 2
  // empty -> -1

  var state by mutableStateOf(State())
    private set
  private val winningCombinations = listOf(
    listOf(0, 1, 2),
    listOf(3, 4, 5),
    listOf(6, 7, 8),
    listOf(0, 3, 6),
    listOf(1, 4, 7),
    listOf(2, 5, 8),
    listOf(0, 4, 8),
    listOf(2, 4, 6)
  )


  fun findWinner() {
    for (combination in winningCombinations) {
      if (state.board[combination[0]] == state.board[combination[1]] &&
        state.board[combination[1]] == state.board[combination[2]] &&
        state.board[combination[0]] != -1 && state.board[combination[1]] == 1
      ) {
        state = state.copy(
          winner = "X"
        )
      } else if (state.board[combination[0]] == state.board[combination[1]] &&
        state.board[combination[1]] == state.board[combination[2]] &&
        state.board[combination[0]] != -1 && state.board[combination[1]] == 2
      ) {
        state = state.copy(
          winner = "O"
        )
      }
    }
  }

  fun changeBoard(index: Int, char: Char) {
    state = state.copy(
      board = state.board.toMutableList().apply {
        this[index] = if (char == 'X') 1 else 2
      }
    )
  }

  fun restart() {
    state = state.copy(
      winner = null,
      board = List(9) { -1 }
    )
  }
}

data class State(
  val board: List<Int> = List(9) { -1 },
  val winner: String? = null,
)

