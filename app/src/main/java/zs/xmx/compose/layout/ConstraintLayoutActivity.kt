package zs.xmx.compose.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import zs.xmx.compose.R
import zs.xmx.compose.theme.MyTestTheme

class ConstraintLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ConstraintLayoutSample()
                }
            }
        }
    }

    @Composable
    fun ConstraintLayoutSample() {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            //基本使用
            BasicUse()

            //简单示例
            SimpleExample()

            //Chain使用
            ChainExample()

            //ConstraintSet动画
            ConstraintSetExample()

        }

    }

    @Composable
    private fun BasicUse() {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "基本使用", fontWeight = FontWeight.Bold)

        ConstraintLayout {
            //createRefs: 创建多个约束引用, createRef: 创建一个约束引用
            val (button, text) = createRefs()

            //constrainAs 和引用进行关联
            Button(onClick = { /* Do something */ }, modifier = Modifier.constrainAs(button) {
                //Button的top和父布局约束,margin 16
                top.linkTo(parent.top, margin = 16.dp)
            }) {
                Text(text = "Button")
            }

            Text(text = "Text", Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 10.dp)
            })
        }
    }

    @Composable
    private fun SimpleExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "简单示例", fontWeight = FontWeight.Bold)

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            //通过createRefs创建三个引用
            val (imageRef, nameRef, descRef, ageRef, colorRef, timeRef) = createRefs()

            Image(painter = painterResource(id = R.drawable.coon),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(imageRef) {//通过constrainAs将Image与imageRef绑定,并增加约束
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(5)),
                contentScale = ContentScale.Crop)


            /*
                Dimension.fillToConstraints 填充约束条件下所剩的可用空间
                Dimension.preferredWrapContent 内容自适应，但受约束条件影响
                Dimension.wrapContent 内容自适应，不受约束条件影响。
             */
            Text(
                text = "浣熊",
                modifier = Modifier
                    .constrainAs(nameRef) {
                        top.linkTo(imageRef.top, 2.dp)
                        start.linkTo(imageRef.end, 12.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth(),
                fontSize = 18.sp,
                maxLines = 1,
                textAlign = TextAlign.Left,
                overflow = TextOverflow.Ellipsis,
            )

            Text(text = "浣熊属哺乳纲食肉目浣熊科的一种动物。源自北美洲。前爪上有一層角質層，有時候需要浸在水裡使其軟化來提高靈敏度，所以看起來就像是把食物或者其他物品清洗乾淨，故名浣熊",
                modifier = Modifier
                    .constrainAs(descRef) {
                        top.linkTo(nameRef.bottom, 5.dp)
                        start.linkTo(nameRef.start)
                        end.linkTo(parent.end)
                        width = Dimension.preferredWrapContent
                    }
                    .fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(color = Color.Gray, fontSize = 13.sp))

            /**
             *  增加两个Text,使其水平居中对齐
             */
            Text(
                text = "年龄", modifier = Modifier.constrainAs(ageRef) {
                    top.linkTo(descRef.bottom, 5.dp)
                    start.linkTo(nameRef.start)

                }, maxLines = 1, overflow = TextOverflow.Ellipsis, style = TextStyle(
                    color = Color.Gray, fontSize = 15.sp, fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "颜色",
                modifier = Modifier.constrainAs(colorRef) {
                    start.linkTo(ageRef.end, 10.dp)
                    centerVerticallyTo(ageRef)//竖直方向居中对齐
                    width = Dimension.fillToConstraints
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(color = Color.Gray, fontSize = 13.sp)
            )

            /**
             * 栅栏
             * ps: 比方"年龄","颜色"的大小不确定,我们希望在根据两者的最底部进行约束
             */
            val bottomBarrier = createBottomBarrier(ageRef, colorRef, margin = 5.dp)

            Text(
                text = "2023-11-07",
                modifier = Modifier.constrainAs(timeRef) {
                    start.linkTo(nameRef.start)
                    top.linkTo(bottomBarrier)
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(color = Color.Gray, fontSize = 13.sp)
            )

        }

    }

    @Composable
    private fun ChainExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "Chain使用示例", fontWeight = FontWeight.Bold)

        Text(text = "Packed(紧贴)", modifier = Modifier.padding(top = 5.dp))
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (btn1, btn2, btn3) = createRefs()
            createHorizontalChain(btn1, btn2, btn3, chainStyle = ChainStyle.Packed)

            Button(onClick = { /* Do something */ },
                shape = RectangleShape,
                modifier = Modifier.constrainAs(btn1) {
                    top.linkTo(parent.top, margin = 5.dp)
                }) {
                Text(text = "A")
            }

            Button(onClick = { /* Do something */ },
                shape = RectangleShape,
                modifier = Modifier.constrainAs(btn2) {
                    top.linkTo(parent.top, margin = 5.dp)
                }) {
                Text(text = "B")
            }

            Button(onClick = { /* Do something */ },
                shape = RectangleShape,
                modifier = Modifier.constrainAs(btn3) {
                    top.linkTo(parent.top, margin = 5.dp)
                }) {
                Text(text = "C")
            }
        }

        Text(text = "Spread(间隔均分)", modifier = Modifier.padding(top = 5.dp))
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (btn1, btn2, btn3) = createRefs()
            createHorizontalChain(btn1, btn2, btn3, chainStyle = ChainStyle.Spread)

            Button(onClick = { /* Do something */ },
                shape = RectangleShape,
                modifier = Modifier.constrainAs(btn1) {
                    top.linkTo(parent.top, margin = 5.dp)
                }) {
                Text(text = "A")
            }

            Button(onClick = { /* Do something */ },
                shape = RectangleShape,
                modifier = Modifier.constrainAs(btn2) {
                    top.linkTo(parent.top, margin = 5.dp)
                }) {
                Text(text = "B")
            }

            Button(onClick = { /* Do something */ },
                shape = RectangleShape,
                modifier = Modifier.constrainAs(btn3) {
                    top.linkTo(parent.top, margin = 5.dp)
                }) {
                Text(text = "C")
            }
        }

        Text(
            text = "SpreadInside(第一个和最后一个贴边,再间隔均分)",
            modifier = Modifier.padding(top = 5.dp)
        )
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (btn1, btn2, btn3) = createRefs()
            createHorizontalChain(btn1, btn2, btn3, chainStyle = ChainStyle.SpreadInside)

            Button(onClick = { /* Do something */ },
                shape = RectangleShape,
                modifier = Modifier.constrainAs(btn1) {
                    top.linkTo(parent.top, margin = 5.dp)
                }) {
                Text(text = "A")
            }

            Button(onClick = { /* Do something */ },
                shape = RectangleShape,
                modifier = Modifier.constrainAs(btn2) {
                    top.linkTo(parent.top, margin = 5.dp)
                }) {
                Text(text = "B")
            }

            Button(onClick = { /* Do something */ },
                shape = RectangleShape,
                modifier = Modifier.constrainAs(btn3) {
                    top.linkTo(parent.top, margin = 5.dp)
                }) {
                Text(text = "C")
            }
        }

    }

    @Composable
    private fun ConstraintSetExample() {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.fillMaxWidth())
        Text(text = "ConstraintSet动画", fontWeight = FontWeight.Bold)

        val orientation = remember { mutableIntStateOf(1) }

        ConstraintLayout(
            getConstraintLayout(orientation),
            Modifier
                .padding(5.dp, 0.dp, 5.dp, 0.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .padding(12.dp, 12.dp, 12.dp, 12.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.coon),
                contentDescription = "效果图片",
                modifier = Modifier
                    .layoutId("imageRef")
                    .fillMaxWidth()
                    .clickable {
                        if (orientation.intValue == 0) {
                            orientation.intValue = 1
                        } else {
                            orientation.intValue = 0
                        }
                    }
                    .clip(shape = RoundedCornerShape(5)),
                contentScale = ContentScale.Crop)

            Text(
                text = "浣熊属哺乳纲食肉目浣熊科的一种动物。源自北美洲。前爪上有一層角質層，有時候需要浸在水裡使其軟化來提高靈敏度，所以看起來就像是把食物或者其他物品清洗乾淨，故名浣熊",
                modifier = Modifier.layoutId("titleRef"),
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                overflow = TextOverflow.Ellipsis,
                maxLines = if (orientation.intValue == 0) Int.MAX_VALUE else 4,
            )
        }
    }

    private fun getConstraintLayout(orientation: MutableState<Int>): ConstraintSet {
        return ConstraintSet {
            val imageRef = createRefFor("imageRef")
            val titleRef = createRefFor("titleRef")

            if (orientation.value == 0) {
                constrain(imageRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }

                constrain(titleRef) {
                    start.linkTo(imageRef.start)
                    end.linkTo(imageRef.end)
                    top.linkTo(imageRef.bottom, 16.dp)
                    width = Dimension.fillToConstraints
                }
            } else {
                constrain(imageRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    width = Dimension.value(100.dp)
                    height = Dimension.value(100.dp)
                }

                constrain(titleRef) {
                    start.linkTo(imageRef.end, 8.dp)
                    top.linkTo(imageRef.top, 2.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(imageRef.bottom, 8.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            }
        }
    }

}