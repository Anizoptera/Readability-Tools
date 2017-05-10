package azadev.toolset.readability

import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.jsoup.select.Elements


class Readability
{
	private var bestScore = 0f
	private var bestElem: Element? = null

	val result: Element? get() = bestElem


	fun process(elems: Elements): Element? {
		elems.forEach { getScore(it) }
		return result
	}


	private fun getScore(elem: Element): Float {
		var res = 0f

		elem.childNodes().forEach { child ->
			val nodeName = child.nodeName()
			when {
				child is TextNode -> {
					res += child.text().trim().length
				}
				child is Element && nodeName !in IGNORE_TAGS -> {
					res += getScore(child) / 1.333f
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
