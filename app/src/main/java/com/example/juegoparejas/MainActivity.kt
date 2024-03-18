package com.example.juegoparejas

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import kotlinx.coroutines.delay
import java.util.Arrays

class MainActivity : AppCompatActivity() {
    //Imagenes
    lateinit var img1_1: ImageView;
    lateinit var img1_2: ImageView;
    lateinit var img1_3: ImageView;
    lateinit var img2_1: ImageView;
    lateinit var img2_2: ImageView;
    lateinit var img2_3: ImageView;
    lateinit var img3_1: ImageView;
    lateinit var img3_2: ImageView;
    lateinit var img3_3: ImageView;
    lateinit var img4_1: ImageView;
    lateinit var img4_2: ImageView;
    lateinit var img4_3: ImageView;

    lateinit var seleccion1: ImageView;
    lateinit var seleccion2: ImageView;

    //Marcadores
    lateinit var j1: TextView;
    lateinit var j2: TextView;
    lateinit var j1Icono: ImageView;
    lateinit var j2Icono: ImageView;

    //Sonido
    lateinit var botonSonido: ImageView;
    lateinit var sonido: MediaPlayer;
    var sonidoFondo: MediaPlayer?=null;
    var pausa=false

    //Variables
    var imagenes = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    var imageViewMap = mutableMapOf<Int, ImageView>()
    var sel1 = 0
    val sel2 = 0
    var turno = 0
    var puntJ1 = 0
    var puntJ2 = 0
    var contTotal=0;
    lateinit var layout : ConstraintLayout;
    var haySonido=true;

    private fun init() {
        //Inicializar imagenes
        img1_1 = findViewById(R.id.img1_1)
        img1_2 = findViewById(R.id.img1_2)
        img1_3 = findViewById(R.id.img1_3)
        img2_1 = findViewById(R.id.img2_1)
        img2_2 = findViewById(R.id.img2_2)
        img2_3 = findViewById(R.id.img2_3)
        img3_1 = findViewById(R.id.img3_1)
        img3_2 = findViewById(R.id.img3_2)
        img3_3 = findViewById(R.id.img3_3)
        img4_1 = findViewById(R.id.img4_1)
        img4_2 = findViewById(R.id.img4_2)
        img4_3 = findViewById(R.id.img4_3)

        //Inicializar marcadores
        j1 = findViewById(R.id.j1)
        j2 = findViewById(R.id.j2)
        j1Icono=findViewById(R.id.j1Icono)
        j2Icono=findViewById(R.id.j2Icono)
        j1Icono.isVisible=true
        turno = 0

        //Inicializar musica
        sonidoFondo = MediaPlayer.create(this, R.raw.cancion_fondo)
        sonidoFondo?.isLooping = true
        sonidoFondo?.start()
        sonidoFondo?.setVolume(0.5f,0.5f)

        //Inicializar boton sonido
        botonSonido=findViewById(R.id.botonSonido)

        //Inicializar layout
        layout=findViewById(R.id.contraint_layout)

        //Boton sonido inicializar
        botonSonido.setOnClickListener(){
            pausa = if(!pausa){
                sonidoFondo?.pause()
                if(this::sonido.isInitialized) {
                    sonido?.setVolume(0f, 0f)
                }
                botonSonido.setImageResource(R.drawable.ic_muteado)
                haySonido=false
                true
            }else{
                sonidoFondo?.start()
                if(this::sonido.isInitialized) {
                    sonido?.setVolume(1.0f, 1.0f)
                }
                haySonido=true
                botonSonido.setImageResource(R.drawable.ic_sonido)
                false
            }
        }

        //Mezclar orden de las keys del mapa
        imagenes.shuffle();

        //Guardo las imagenes en el mapa asignandolas un key aleatorio
        imageViewMap.put(imagenes[0], img1_1)
        imageViewMap.put(imagenes[1], img1_2)
        imageViewMap.put(imagenes[2], img1_3)
        imageViewMap.put(imagenes[3], img2_1)
        imageViewMap.put(imagenes[4], img2_2)
        imageViewMap.put(imagenes[5], img2_3)
        imageViewMap.put(imagenes[6], img3_1)
        imageViewMap.put(imagenes[7], img3_2)
        imageViewMap.put(imagenes[8], img3_3)
        imageViewMap.put(imagenes[9], img4_1)
        imageViewMap.put(imagenes[10], img4_2)
        imageViewMap.put(imagenes[11], img4_3)

        //Recorro el mapa para asignar a cada imagen del mapa el evento seleccionar, cuyo funcionamiento depende del key
        for ((key, value) in imageViewMap) {
            value.setOnClickListener {
                seleccionar(key, value)
            }
            value.setImageResource(R.drawable.oculta)
        }
    }
    private fun sonidoSeleccionar(id:Int){
        if(haySonido) {
            when (id) {
                1, 2 -> {
                    if (this::sonido.isInitialized)
                        sonido.stop()
                    sonido = MediaPlayer.create(this, R.raw.bob_esponja)
                    sonido.start()
                }

                3, 4 -> {
                    if (this::sonido.isInitialized)
                        sonido.stop()
                    sonido = MediaPlayer.create(this, R.raw.patricio)
                    sonido.start()
                }

                5, 6 -> {
                    if (this::sonido.isInitialized)
                        sonido.stop()
                    sonido = MediaPlayer.create(this, R.raw.calamardo)
                    sonido.start()
                }

                7, 8 -> {
                    if (this::sonido.isInitialized)
                        sonido.stop()
                    sonido = MediaPlayer.create(this, R.raw.arenita)
                    sonido.start()
                }

                9, 10 -> {
                    if (this::sonido.isInitialized)
                        sonido.stop()
                    sonido = MediaPlayer.create(this, R.raw.cangrejo)
                    sonido.start()
                }

                11, 12 -> {
                    if (this::sonido.isInitialized)
                        sonido.stop()
                    sonido = MediaPlayer.create(this, R.raw.plankton)
                    sonido.start()
                }
            }
        }
    }
    private fun seleccionar(id: Int, img: ImageView) {
        val girar = ObjectAnimator.ofFloat(img, "rotationY", 0f, 90f)
        val girarVuelta = ObjectAnimator.ofFloat(img, "rotationY", 90f, 0f)
        girar.duration = 500
        girarVuelta.duration = 500

        sonidoSeleccionar(id)
        girar.start()
        girar.doOnEnd {
            when (id) {
                1, 2 ->{
                    img.setImageResource(R.drawable.bobesponja)
                }
                3, 4 ->{
                    img.setImageResource(R.drawable.patricio)
                }
                5, 6 ->{
                    img.setImageResource(R.drawable.calamardo)
                }
                7, 8 ->{
                    img.setImageResource(R.drawable.arenita)
                }
                9, 10 ->{
                    img.setImageResource(R.drawable.cangrejo)
                }
                11, 12 ->{
                    img.setImageResource(R.drawable.plankton)
                }
            }
            girarVuelta.start()
        }

        //Si no hay nada seleccionado almaceno la seleccion
        if (sel1 == 0) {
            sel1 = id;
            img.isEnabled=false
        }//Si ya hay algo seleccionado compruebo si son la misma imagen, si el primer numero seleccionado es par le resto uno, sino le sumo uno,por la formula usada en el when
        else {
            if (sel1 % 2 == 0 && sel1 == id + 1) {
                aumentarMarcador(sel1,id)
                if(haySonido&&this::sonido.isInitialized){
                    sonido.pause()
                    sonido=MediaPlayer.create(this, R.raw.acierto)
                    sonido.start()
                }

            }//Si es impar le sumo uno
            else if (sel1 % 2 != 0 && sel1 == id - 1) {
                aumentarMarcador(sel1,id)
                if(haySonido&&this::sonido.isInitialized){
                    sonido.pause()
                    sonido=MediaPlayer.create(this, R.raw.acierto)
                    sonido.start()
                }

            }

            else {
                //Cambio las imagenes a ocultas despues de un delay de 1 segundo
                val handler = Handler(Looper.getMainLooper())
                val img2 = imageViewMap[sel1]

                val girar = ObjectAnimator.ofFloat(img, "rotationY", 0f, 90f)
                val girarVuelta = ObjectAnimator.ofFloat(img, "rotationY", 90f, 0f)

                val girar2 = ObjectAnimator.ofFloat(img2, "rotationY", 0f, 90f)
                val girarVuelta2 = ObjectAnimator.ofFloat(img2, "rotationY", 90f, 0f)

                girar.duration = 500
                girarVuelta.duration = 500

                girar2.duration = 500
                girarVuelta2.duration = 500

                disableAll()

                handler.postDelayed({
                    if(haySonido&&this::sonido.isInitialized) {
                        sonido.stop()
                        sonido = MediaPlayer.create(this, R.raw.fracaso)
                        sonido.start()
                    }
                    girar.start()
                    girar.doOnEnd {
                        img.setImageResource(R.drawable.oculta)
                        girarVuelta.start()
                    }
                    girar2.start()
                    girar2.doOnEnd {
                        img2?.setImageResource(R.drawable.oculta)
                        girarVuelta2.start()
                        turno = if (turno == 0) {
                            j1Icono.isVisible=false
                            j2Icono.isVisible=true
                            1
                        } else {
                            j1Icono.isVisible=true
                            j2Icono.isVisible=false
                            0
                        }
                    }
                    enableAll()
                }, 2000)
            }
            sel1 = 0
        }
    }
    private fun victoria() {
        val botonReseteo = Button(this)
        val textoVictoria : TextView= findViewById(R.id.textoVictoria)
        botonReseteo.text = "Volver a jugar"

        val parametrosBoton = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, // width
            ConstraintLayout.LayoutParams.WRAP_CONTENT  // height
        )

        parametrosBoton.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        parametrosBoton.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        parametrosBoton.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        parametrosBoton.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

        botonReseteo.layoutParams=parametrosBoton
        botonReseteo.setBackgroundColor(Color.GREEN)
        botonReseteo.setPadding(10,10,10,10)


        botonReseteo.setOnClickListener {
            enableAll()
            init()
            layout.removeView(botonReseteo)
            textoVictoria.isVisible=false
        }
        layout.addView(botonReseteo)


        if(puntJ1>puntJ2){
            textoVictoria.text="Enorabuena J1, has ganado"
            textoVictoria.setBackgroundColor(Color.parseColor("#673AB7"))
            textoVictoria.setTextColor(Color.WHITE)
        }
        else if(puntJ2>puntJ1){
            textoVictoria.text="Enorabuena J2, has ganado"
            textoVictoria.setBackgroundColor(Color.parseColor("#C8380A"))
            textoVictoria.setTextColor(Color.WHITE)
        }
        else{
            textoVictoria.text="Empate, no ha habido ganador"
            textoVictoria.setBackgroundColor(Color.DKGRAY)
            textoVictoria.setTextColor(Color.BLACK)
        }
        textoVictoria.isVisible=true
    }

    private fun aumentarMarcador(sel1:Int,sel2:Int){
        //Dependiendo de quien sea el turno sumo al marcador de j1 o j2
        if (turno == 0) {
            puntJ1++
            j1.text = "J1:" + puntJ1
            j1Icono.isVisible=false
            j2Icono.isVisible=true
            turno = 1
        } else {
            puntJ2++
            j2.text = "J2:" + puntJ2
            j1Icono.isVisible=true
            j2Icono.isVisible=false
            turno = 0
        }
        //Desactivo las imagenes y las quito del mapa para que no puedan volver a ser activadas
        imageViewMap[sel1]?.isEnabled  = false
        imageViewMap[sel2]?.isEnabled  = false
        imageViewMap.remove(sel1)
        imageViewMap.remove(sel2)

        contTotal++
        if(contTotal==1){
            victoria()
        }
    }
    private fun disableAll() {
        for ((key, value) in imageViewMap) {
            value.isEnabled = false
        }
    }

    private fun enableAll() {
        for ((key, value) in imageViewMap) {
            value.isEnabled = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        print(Arrays.toString(imagenes))
    }

    //Quitar musica al cerrar la aplicacion
    override fun onDestroy() {
        super.onDestroy()
        sonidoFondo?.stop()
        sonidoFondo?.release()
        sonidoFondo = null
    }

    //Pausar musica al salir de la aplicacion
    override fun onPause() {
        super.onPause()
        pausarSonidoFondo()
    }
    fun pausarSonidoFondo(){
        sonidoFondo?.pause()
    }

    //Renaudar musica al volver a entrar en la aplicacion
    override fun onResume() {
        super.onResume()
        renaudarSonidoFondo()
    }
    fun renaudarSonidoFondo(){
        sonidoFondo?.start()
    }
}