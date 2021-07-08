package com.example.myapplication2

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.rendering.ViewRenderable
import com.rosefinches.smiledialog.SmileDialogBuilder
import com.rosefinches.smiledialog.enums.SmileDialogType
import kotlinx.android.synthetic.main.activity_measure.*
import java.util.*
import kotlin.concurrent.schedule

data class AnchorInfoBean(
        var dataText: String,
        var anchor: Anchor,
        var length: Double
)

class FaceToCameraNode : Node() {
    override fun onUpdate(p0: FrameTime?) {
        scene?.let { scene ->
            val cameraPosition = scene.camera.worldPosition
            val nodePosition = this@FaceToCameraNode.worldPosition
            val direction = Vector3.subtract(cameraPosition, nodePosition)
            this@FaceToCameraNode.worldRotation = Quaternion.lookRotation(direction, Vector3.up())
        }
    }
}

class Measure : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)// 设置全屏
        setContentView(R.layout.activity_measure)
        initView()

        val dialog = SmileDialogBuilder(this@Measure, SmileDialogType.WARNING)
                .setContentText("""
                    将手机按照屏幕提示移动，尽量选择一个光滑的平面。
                    待屏幕出现标点(白色圆球)提示时，选择两点进行量测。
                    点击左下角“回退键”取消标点，点击左上角“确定键”上传距离。
                    """.trimIndent()) //设置内容文字
                .setConformButton("确定") //设置确认按钮文字
                .setConformTextColor(R.color.white) //设置确认按钮文字颜色
                .setConformBgResColor(R.color.yellow_dark) //设置确认按钮背景颜色
                .setContentTextColor(Color.BLACK) //设置内容字体颜色
                .setCanceledOnTouchOutside(true) //点击区域外是否消失
                .setWindowAnimations(R.style.dialog_style) //设置动画style
                .build()

        dialog.show()


        back.setOnClickListener{
//            var str=distance.toString();
            var str = 0.5.toString();
            MainActivity.getString(str);
            println("distance:$str");

            showMessage(if (MainActivity.uploadFile("/storage/emulated/0/Android/data/com.example.myapplication2/files/hello.txt", "distance.txt")) "上传成功" else "上传失败")
            val intent = Intent(this@Measure, transformView::class.java)
//            intent.putExtra("imagelistArr", imagelist)
            startActivity(intent)
            Timer().schedule(2000){
                finish();
            }
        }

        sure_message.setOnClickListener{
            val dialog2 = SmileDialogBuilder(this@Measure, SmileDialogType.WARNING)
                    .setContentText("""
                    将手机按照屏幕提示移动，尽量选择一个光滑的平面。
                    待屏幕出现标点(白色圆球)提示时，选择两点进行量测。
                    点击左下角“回退键”取消标点，点击左上角“确定键”上传距离。
                    """.trimIndent()) //设置内容文字
                    .setConformButton("确定") //设置确认按钮文字
                    .setConformTextColor(R.color.white) //设置确认按钮文字颜色
                    .setConformBgResColor(R.color.yellow_dark) //设置确认按钮背景颜色
                    .setContentTextColor(Color.BLACK) //设置内容字体颜色
                    .setCanceledOnTouchOutside(true) //点击区域外是否消失
//                    .setWindowAnimations(R.style.dialog_style) //设置动画style
                    .build()
           dialog2.show()
        }
    }
    fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun initView() {

        UI_Last.setOnClickListener {
            //上一步
            when (dataArray.size) {
                0 -> {
                    ToastUtils.showLong("没有操作记录")
                }
                1 -> {
                    dataArray.clear()
                    lineNodeArray.clear()
                    sphereNodeArray.clear()
                    startNodeArray.clear()
                    endNodeArray.clear()
                    (UI_ArSceneView as MyArFragment).arSceneView.scene.removeChild(startNode)
                }
                else -> {
                    dataArray.removeAt(dataArray.size - 1)
                    val index = startNodeArray.size - 1
                    startNodeArray[index].removeChild(lineNodeArray.removeAt(index))
                    endNodeArray[index].removeChild(sphereNodeArray.removeAt(index + 1))
                    (UI_ArSceneView as MyArFragment).arSceneView.scene.removeChild(startNodeArray.removeAt(index))
                    (UI_ArSceneView as MyArFragment).arSceneView.scene.removeChild(endNodeArray.removeAt(index))
                }
            }
        }

/*        UI_Post.setOnClickListener {
            //发送
            if (dataArray.size < 3) {
                ToastUtils.showLong("最少三个点")
                return@setOnClickListener
            }
            val tempJsonArray = arrayListOf<Float>()

            dataArray.forEachIndexed { index, anchorInfoBean ->
                if (index == dataArray.size - 1) {
                    val startPose = dataArray[0].anchor.pose
                    val endPose = anchorInfoBean.anchor.pose
                    val dx = startPose.tx() - endPose.tx()
                    val dy = startPose.ty() - endPose.ty()
                    val dz = startPose.tz() - endPose.tz()
                    if (Math.sqrt((dx * dx + dy * dy + dz * dz).toDouble()) > 1) {
                        val node = AnchorNode(anchorInfoBean.anchor)
                        tempJsonArray.add(node.worldPosition.x)
                        tempJsonArray.add(node.worldPosition.z)
                    } else {
                    }
                } else {
                    val node = AnchorNode(anchorInfoBean.anchor)
                    tempJsonArray.add(node.worldPosition.x)
                    tempJsonArray.add(node.worldPosition.z)
                }
            }

            ActivityUtils.startActivity( Intent().apply {
                setClass(this@MainActivity, WebActivity::class.java)
                putExtra("url", "http://47.100.46.19/demo/example/index.html?points=${Gson().toJson(tempJsonArray)}")
            })
        }*/
        initAr()
    }

    private val dataArray = arrayListOf<AnchorInfoBean>()
    private val lineNodeArray = arrayListOf<Node>()
    private val sphereNodeArray = arrayListOf<Node>()
    private val startNodeArray = arrayListOf<Node>()
    private val endNodeArray = arrayListOf<Node>()
    private var distance =0.0;
    private  lateinit var startNode: AnchorNode

    @SuppressLint("NewApi")
    private fun initAr() {
        (UI_ArSceneView as MyArFragment).setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            val anchorInfoBean = AnchorInfoBean("", hitResult.createAnchor(), 0.0)
            dataArray.add(anchorInfoBean)

            if (dataArray.size > 1) {
                val endAnchor = dataArray[dataArray.size - 1].anchor
                val startAnchor = dataArray[dataArray.size - 2].anchor


                val startPose = endAnchor.pose
                val endPose = startAnchor.pose
                val dx = startPose.tx() - endPose.tx()
                val dy = startPose.ty() - endPose.ty()
                val dz = startPose.tz() - endPose.tz()

                anchorInfoBean.length = Math.sqrt((dx * dx + dy * dy + dz * dz).toDouble())
                distance= anchorInfoBean.length;
                drawLine(startAnchor, endAnchor, anchorInfoBean.length)
            } else {
                startNode = AnchorNode(hitResult.createAnchor())
                startNode.setParent((UI_ArSceneView as MyArFragment).arSceneView.scene)
                MaterialFactory.makeOpaqueWithColor(this@Measure, com.google.ar.sceneform.rendering.Color(0.33f, 0.87f, 0f))
                        .thenAccept { material ->
                            val sphere = ShapeFactory.makeSphere(0.02f, Vector3.zero(), material)
                            sphereNodeArray.add(    Node().apply {
                                setParent(startNode)
                                localPosition = Vector3.zero()
                                renderable = sphere
                            })
                        }
            }
        }
    }

    private fun drawLine(firstAnchor: Anchor, secondAnchor: Anchor, length: Double) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val firstAnchorNode = AnchorNode(firstAnchor)
            startNodeArray.add(firstAnchorNode)

            val secondAnchorNode = AnchorNode(secondAnchor)
            endNodeArray.add(secondAnchorNode)

            firstAnchorNode.setParent((UI_ArSceneView as MyArFragment).arSceneView.scene)
            secondAnchorNode.setParent((UI_ArSceneView as MyArFragment).arSceneView.scene)

            MaterialFactory.makeOpaqueWithColor(this@Measure, com.google.ar.sceneform.rendering.Color(0.53f, 0.92f, 0f))
                    .thenAccept { material ->
                        val sphere = ShapeFactory.makeSphere(0.02f, Vector3(0.0f, 0.0f, 0.0f), material)
                        sphereNodeArray.add(Node().apply {
                            setParent(secondAnchorNode)
                            localPosition = Vector3.zero()
                            renderable = sphere
                        })
                    }

            val firstWorldPosition = firstAnchorNode.worldPosition
            val secondWorldPosition = secondAnchorNode.worldPosition

            val difference = Vector3.subtract(firstWorldPosition, secondWorldPosition)
            val directionFromTopToBottom = difference.normalized()
            val rotationFromAToB = Quaternion.lookRotation(directionFromTopToBottom, Vector3.up())

            MaterialFactory.makeOpaqueWithColor(this@Measure, com.google.ar.sceneform.rendering.Color(0.33f, 0.87f, 0f))
                    .thenAccept { material ->
                        val lineMode = ShapeFactory.makeCube(Vector3(0.01f, 0.01f, difference.length()), Vector3.zero(), material)
                        val lineNode = Node().apply {
                            setParent(firstAnchorNode)
                            renderable = lineMode
                            worldPosition = Vector3.add(firstWorldPosition, secondWorldPosition).scaled(0.5f)
                            worldRotation = rotationFromAToB
                        }
                        lineNodeArray.add(Node().apply {
                            setParent(firstAnchorNode)
                            renderable = lineMode
                            worldPosition = Vector3.add(firstWorldPosition, secondWorldPosition).scaled(0.5f)
                            worldRotation = rotationFromAToB
                        })

                        ViewRenderable.builder()
                                .setView(this@Measure, R.layout.renderable_text)
                                .build()
                                .thenAccept { it ->
                                    (it.view as TextView).text = "${String.format("%.2f", length * 100)}CM"
                                    it.isShadowCaster = false
                                    FaceToCameraNode().apply {
                                        setParent(lineNode)
                                        localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 90f)
                                        localPosition = Vector3(0f, 0.02f, 0f)
                                        renderable = it
                                    }
                                }
                    }
        }
    }

    /*override fun onDestroy() {
        super.onDestroy()
        (UI_ArSceneView as MyArFragment).onDestroy()
    }*/
}