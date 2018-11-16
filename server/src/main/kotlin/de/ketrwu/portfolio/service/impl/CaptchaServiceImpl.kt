package de.ketrwu.portfolio.service.impl

import de.ketrwu.portfolio.Slf4j
import de.ketrwu.portfolio.entity.CaptchaForm
import de.ketrwu.portfolio.service.CaptchaService
import org.apache.commons.lang.RandomStringUtils
import org.slf4j.Logger
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.Font
import java.awt.FontFormatException
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Base64
import java.util.Random
import java.util.UUID
import javax.imageio.ImageIO
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.set

/**
 * Service to create captcha images for the frontend and validate captcha answers
 * @author Kenneth Wußmann
 */
@Service
class CaptchaServiceImpl : CaptchaService {

    private val solutions = HashMap<String, String>()
    private val fonts = ArrayList<Font>()

    private val randomFont: Font
        get() = fonts[RANDOM.nextInt(fonts.size)]

    private val randomString: String
        get() = RandomStringUtils.random(
            RANDOM.nextInt(SOLUTION_LENGTH[1] - SOLUTION_LENGTH[0] + 1) + SOLUTION_LENGTH[0],
            true,
            true
        ).toLowerCase()

    /**
     * {@inheritDoc}
     */
    @Suppress("MagicNumber")
    @Throws(IOException::class)
    override fun createCaptcha(captchaForm: CaptchaForm): String {
        val solution = randomString

        UUID.randomUUID().toString().let { uuid ->
            captchaForm.captchaToken = uuid
            solutions[uuid] = solution
        }

        val img = BufferedImage(IMG_SIZE[0], IMG_SIZE[1], BufferedImage.TYPE_INT_ARGB)
        val g2d = img.createGraphics()
        g2d.paint = Color.white
        g2d.font = randomFont
        val solutionBytes = solution.toByteArray()

        for (i in solutionBytes.indices) {
            val at = AffineTransform()
            val x = (Math.random() * 10).toInt() + i * 20
            at.shear(0.2 * i, 0.0)
            g2d.transform = at
            g2d.drawString(
                String(byteArrayOf(solutionBytes[i])),
                x,
                (Math.random() * 10 + 20).toInt()
            )
        }

        g2d.dispose()

        val os = ByteArrayOutputStream()
        ImageIO.write(img, "png", os)

        return Base64.getEncoder().encodeToString(os.toByteArray()).let { encoded ->
            captchaForm.captchaImage = encoded
            encoded
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun checkCaptcha(captchaForm: CaptchaForm): Boolean {
        if (captchaForm.captchaToken?.trim { it <= ' ' }?.isEmpty() != false) {
            return false
        }
        if (captchaForm.captchaResponse?.trim { it <= ' ' }?.isEmpty() != false) {
            return false
        }
        if (captchaForm.captchaToken?.let { solutions[it] == null } == true) {
            return false
        }

        return captchaForm.captchaToken?.let { captchaToken ->
            val solution = solutions[captchaToken]
            solutions.remove(captchaToken)
            captchaForm.captchaResponse?.let {
                solution.equals(it, true)
            }
        } ?: false
    }

    /**
     * {@inheritDoc}
     */
    @EventListener(ApplicationReadyEvent::class)
    override fun loadFonts() {
        fonts.clear()
        val cl = this.javaClass.classLoader
        val resolver = PathMatchingResourcePatternResolver(cl)
        try {
            for (resource in resolver.getResources("classpath*:/static/captcha-fonts/*.ttf")) {
                createFont(resource)?.let {
                    fonts.add(it)
                }
            }
        } catch (e: IOException) {
            log.error("Failed to find fonts in classpath", e)
        }
    }

    private fun createFont(resource: Resource): Font? {
        var font: Font? = null
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, resource.inputStream)
            font = font?.deriveFont(FONT_SIZE)
        } catch (e: FontFormatException) {
            log.error("Failed to create font of file \"{}\"", resource.filename, e)
        } catch (e: IOException) {
            log.error("Failed to create font of file \"{}\"", resource.filename, e)
        }
        return font
    }

    companion object {
        private val RANDOM = Random()
        private val IMG_SIZE = intArrayOf(180, 40)
        private val SOLUTION_LENGTH = intArrayOf(4, 7)
        private const val FONT_SIZE = 20f

        @Slf4j
        lateinit var log: Logger
    }
}
