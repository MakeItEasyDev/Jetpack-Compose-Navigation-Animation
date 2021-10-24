package com.jetpack.navigationanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.jetpack.navigationanimation.ui.theme.NavigationAnimationTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationAnimationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavAnimation()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun NavAnimation() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = "Screen_one"
    ) {
        composable(
            "Screen_one",
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Screen_two" ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(800))
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Screen_two" ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(800))
                    else -> null
                }
            },
            popEnterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Screen_two" ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(800))
                    else -> null
                }
            },
            popExitTransition = { _, target ->
                when (target.destination.route) {
                    "Screen_two" ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(800))
                    else -> null
                }
            }
        ) {
            ScreenOne(navController)
        }

        //Second Screen
        composable(
            "Screen_two",
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Screen_one" ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(800))
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    "Screen_one" ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(800))
                    else -> null
                }
            },
            popEnterTransition = { initial, _ ->
                when (initial.destination.route) {
                    "Screen_one" ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(800))
                    else -> null
                }
            },
            popExitTransition = { _, target ->
                when (target.destination.route) {
                    "Screen_one" ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(800))
                    else -> null
                }
            }
        ) {
            ScreenTwo(navController)
        }
    }
}

@Composable
fun ScreenOne(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SampleButton(
            "Navigate Horizontal",
            Modifier
                .wrapContentWidth()
                .then(Modifier.align(Alignment.CenterHorizontally))
        ) {
            navController.navigate("Screen_two")
        }
    }
}

@Composable
fun ScreenTwo(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BackButton(navController)
    }
}

@Composable
fun SampleButton(
    text: String,
    modifier: Modifier = Modifier,
    listener: () -> Unit = {}
) {
    Button(
        onClick = listener,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
    ) {
        Text(
            text = text
        )
    }
}

@Composable
fun BackButton(
    navController: NavController
) {
    if (navController.currentBackStackEntry == LocalLifecycleOwner.current &&
            navController.previousBackStackEntry != null
    ) {
        Button(
            onClick = {
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = "Go to Previous Screen"
            )
        }
    }
}























