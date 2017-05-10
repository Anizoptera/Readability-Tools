package azadev.toolset.readability

import org.jsoup.nodes.*
import org.jsoup.select.Elements


class Readability(
		val bubblingRate: Float = 1.333f
) {
	private var bestScore = 0f
	private var bestElem: Element? = null

	val result: Element? get() = bestElem


	fun process(elems: Elements): Element? {
		if (bestElem != null)
			throw IllegalStateException("'process()' was called already; this method may be called only once for each instance of Readability")

		elems.forEach { getScore(it) }
		return result
	}

	fun process(doc: Document) = process(doc.children())


	private fun getScore(elem: Element): Float {
		var res = 0f

		elem.childNodes().forEach { child ->
			val nodeName = child.nodeName()
			when {
				child is TextNode -> {
					res += child.text().trim().length
				}
				child is Element && nodeName !in IGNORE_TAGS -> {
					res += getScore(child) / bubblingRate
				}
			}
		}

		if (res > bestScore) {
			bestScore = res
			bestElem = elem
		}

		return res
	}


	companion object
	{
		private val IGNORE_TAGS = arrayOf("head", "meta", "script", "link", "style", "select")
	}
}
