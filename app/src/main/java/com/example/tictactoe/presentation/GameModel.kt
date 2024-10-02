package com.example.tictactoe.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tictactoe.presentation.shape.Cross
import com.example.tictactoe.presentation.shape.GameBoard
import com.example.tictactoe.presentation.shape.Zero

@Composable
fun GameModel(modifier: Modifier = Modifier, viewModel: GameModelViewModel = hiltViewModel()) {

  var gameState by rememberSaveable {
    mutableStateOf(List(9) { "" })
  }
  var playerState by rememberSaveable {
    mutableStateOf('X')
  }
  var gameOn by rememberSaveable {
    mutableStateOf(true)
  }
  var pressCount by rememberSaveable { mutableIntStateOf(0) }
  val indication = remember { MutableInteractionSource() }
  val state = viewModel.state

  if (pressCount >= 5 && gameOn) {
    LaunchedEffect(state) {
      viewModel.findWinner()
      if (state.winner != null) {
        gameOn = false
      }
    }
  }

  Column(
    modifier.fillMaxSize()
      .systemBarsPadding()
      .statusBarsPadding()
      .background(MaterialTheme.colorScheme.primary),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Box(
      modifier.padding(20.dp),
    ) {
      Text(
        modifier = modifier.fillMaxWidth(),
        text = if (gameOn) "Its '$playerState' Turn" else "WINNER IS ${state.winner}",
        color = MaterialTheme.colorScheme.secondary,
        style = TextStyle(
          textAlign = TextAlign.Center,
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace
        )
      )
    }
    Surface(
      modifier
        .fillMaxHeight(0.55f)
        .fillMaxWidth(),
      shape = RoundedCornerShape(30.dp),
      color = MaterialTheme.colorScheme.tertiary,
    ) {
      GameBoard(
        modifier = Modifier.padding(20.dp)
      )
      LazyVerticalGrid(
        modifier = Modifier
          .fillMaxSize()
          .padding(10.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
      ) {
        items(9) {
          Box(
            modifier
              .size(120.dp)
              .weight(1f)
              .padding(2.dp)
              .clickable(
                enabled = gameOn,
                interactionSource = indication,
                indication = null,
                onClick = {
                  pressCount += 1
                  if (gameState[it].isEmpty()) {
                    viewModel.changeBoard(it, playerState)
                    gameState = gameState
                      .toMutableList()
                      .apply {
                        this[it] = playerState.toString()
                      }
                    playerState = if (playerState == 'X') 'O' else 'X'
                  }
                }
              ),
            contentAlignment = Alignment.Center
          ) {
            if (gameState[it] == 'X'.toString()) {
              Cross(color = MaterialTheme.colorScheme.secondary)
            }
            if (gameState[it] == 'O'.toString()) {
              Zero(color = MaterialTheme.colorScheme.secondary)
            }
          }
        }
      }
    }

    Box(
      modifier
        .fillMaxHeight(0.2f)
        .fillMaxWidth(),
      contentAlignment = Alignment.Center
    ) {
      if (!gameOn || pressCount == 9) {
        Button(
          modifier = Modifier
            .fillMaxWidth(0.5f),
          onClick = {
            gameOn = true
            gameState = List(9) { "" }
            playerState = 'X'
            pressCount = 0
            viewModel.restart()
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
          )
        ) {
          Text(
            modifier = modifier.fillMaxWidth(),
            text = "RESTART",
            color = MaterialTheme.colorScheme.primary,
            style = TextStyle(
              textAlign = TextAlign.Center,
              fontSize = 24.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
            )
          )
        }
      }
    }
  }
}