package dev.brunog.dairyapp.model

import androidx.compose.ui.graphics.Color
import dev.brunog.dairyapp.R
import dev.brunog.dairyapp.ui.theme.AngryColor
import dev.brunog.dairyapp.ui.theme.AwfulColor
import dev.brunog.dairyapp.ui.theme.BoredColor
import dev.brunog.dairyapp.ui.theme.CalmColor
import dev.brunog.dairyapp.ui.theme.DepressedColor
import dev.brunog.dairyapp.ui.theme.DisappointedColor
import dev.brunog.dairyapp.ui.theme.HappyColor
import dev.brunog.dairyapp.ui.theme.HumorousColor
import dev.brunog.dairyapp.ui.theme.LonelyColor
import dev.brunog.dairyapp.ui.theme.MysteriousColor
import dev.brunog.dairyapp.ui.theme.NeutralColor
import dev.brunog.dairyapp.ui.theme.RomanticColor
import dev.brunog.dairyapp.ui.theme.ShamefulColor
import dev.brunog.dairyapp.ui.theme.SurprisedColor
import dev.brunog.dairyapp.ui.theme.SuspiciousColor
import dev.brunog.dairyapp.ui.theme.TenseColor

enum class Mood(
    val icon: Int,
    val contentColor: Color,
    val containerColor: Color
) {
    Neutral(
        icon = R.drawable.neutral,
        contentColor = Color.Black,
        containerColor = NeutralColor
    ),
    Happy(
        icon = R.drawable.happy,
        contentColor = Color.Black,
        containerColor = HappyColor
    ),
    Angry(
        icon = R.drawable.angry,
        contentColor = Color.White,
        containerColor = AngryColor
    ),
    Bored(
        icon = R.drawable.bored,
        contentColor = Color.Black,
        containerColor = BoredColor
    ),
    Calm(
        icon = R.drawable.calm,
        contentColor = Color.Black,
        containerColor = CalmColor
    ),
    Depressed(
        icon = R.drawable.depressed,
        contentColor = Color.Black,
        containerColor = DepressedColor
    ),
    Disappointed(
        icon = R.drawable.disappointed,
        contentColor = Color.White,
        containerColor = DisappointedColor
    ),
    Humorous(
        icon = R.drawable.humorous,
        contentColor = Color.Black,
        containerColor = HumorousColor
    ),
    Lonely(
        icon = R.drawable.lonely,
        contentColor = Color.Black,
        containerColor = LonelyColor
    ),
    Mysterious(
        icon = R.drawable.mysterious,
        contentColor = Color.Black,
        containerColor = MysteriousColor
    ),
    Romantic(
        icon = R.drawable.romantic,
        contentColor = Color.White,
        containerColor = RomanticColor
    ),
    Shameful(
        icon = R.drawable.shameful,
        contentColor = Color.White,
        containerColor = ShamefulColor
    ),
    Awful(
        icon = R.drawable.awful,
        contentColor = Color.Black,
        containerColor = AwfulColor
    ),
    Surprised(
        icon = R.drawable.surprised,
        contentColor = Color.Black,
        containerColor = SurprisedColor
    ),
    Suspicious(
        icon = R.drawable.suspicious,
        contentColor = Color.Black,
        containerColor = SuspiciousColor
    ),
    Tense(
        icon = R.drawable.tense,
        contentColor = Color.Black,
        containerColor = TenseColor
    )
}