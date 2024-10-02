package com.example.tictactoe.presentation.shape

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun GameBoard(
  color: Color = Color.Black,
  strokeWidth: Int = 4,
  modifier: Modifier = Modifier
) {
  Canvas(
    modifier = Modifier
      .fillMaxSize()
      .then(modifier)
  ) {
    drawLine(
      color = color,
      start = Offset(x = (size.width / 3) * 1, y = 0f),
      end = Offset(x = (size.width / 3) * 1, y = size.height),
      strokeWidth = strokeWidth.dp.toPx()
    )
    drawLine(
      color = color,
      start = Offset(x = (size.width / 3) * 2, y = 0f),
      end = Offset(x = (size.width / 3) * 2, y = size.height),
      strokeWidth = strokeWidth.dp.toPx()
    )
    drawLine(
      color = color,
      start = Offset(x = 0f, y = (size.height / 3) * 1),
      end = Offset(x = size.width, y = (size.height / 3) * 1),
      strokeWidth = strokeWidth.dp.toPx()
    )
    drawLine(
      color = color,
      start = Offset(x = 0f, y = (size.height / 3) * 2),
      end = Offset(x = size.width, y = (size.height / 3) * 2),
      strokeWidth = strokeWidth.dp.toPx()
    )
  }
}


@Composable
fun Cross(
  color: Color = Color.Cyan,
  strokeWidth: Int = 8,
  modifier: Modifier = Modifier
) {
  Canvas(
    modifier = Modifier
      .fillMaxSize()
      .padding(10.dp)
  ) {
    var sizeW = size.width/100
    var sizeH = size.height/100
    drawLine(
      color = color,
      start = Offset(x = sizeW*20, y = sizeH*20),
      end = Offset(x = sizeW*80, y = sizeH*80),
      strokeWidth = strokeWidth.dp.toPx(),
      cap = StrokeCap.Round
    )
    drawLine(
      color = color,
      start = Offset(x = sizeW*80, y = sizeH*20),
      end = Offset(x = sizeW*20, y = sizeH*80),
      strokeWidth = strokeWidth.dp.toPx(),
      cap = StrokeCap.Round
    )
  }
}


@Composable
fun Zero(
  modifier: Modifier = Modifier,
  color: Color = Color.Green,
  strokeWidth: Int = 8
) {
  Canvas(modifier = Modifier.fillMaxSize()) {
    val circleCenter = Offset(x = size.width / 2, y = size.height / 2)
    val radius = 100f
    drawCircle(
      color = color,
      radius = radius,
      center = circleCenter,
      style = Stroke(width = strokeWidth.dp.toPx())
    )
  }
}

