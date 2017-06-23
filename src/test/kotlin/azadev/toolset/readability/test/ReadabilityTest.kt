package azadev.toolset.readability.test

import azadev.toolset.readability.Readability
import org.jsoup.Jsoup
import org.jsoup.helper.StringUtil
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.junit.*
import org.junit.Assert.assertEquals as eq
import org.junit.Assert.assertTrue as yes


class ReadabilityTest
{
	@Test fun basics() {
		checkFile("001.html")
		checkFile("002.html")
//		checkFile("003.html")


		// https://www.visitportugal.com/en/content/sketch-tour-portugal
		checkFile("visitportugal_01.html")
		// https://www.visitportugal.com/en/node/155972
		checkFile("visitportugal_02.html")

		// https://habrahabr.ru/post/328284/
		checkFile("habrahabr_01.html")

		// https://habrahabr.ru/post/70330/
		// Too many comments are here; Readability thinks it's the main content
//		checkFile("habrahabr_02.html")

		// https://habrahabr.ru/article/330482/
		// Too many comments are here; Readability thinks it's the main content
//		checkFile("habrahabr_mega-01.html")

		// TODO: Try to add a coef that gives better score to those elements, which contain more text (relatively to others)


		// https://meduza.io/shapito/2017/05/09/amerikanets-ustanovil-rekord-po-retvitam-radi-besplatnyh-naggetsov-da-on-sdelal-eto
		checkFile("meduza_01.html")


		// https://en.wikipedia.org/wiki/Veganism
		checkFile("wiki_01.html")
		// https://en.wikipedia.org/wiki/Commodity_status_of_animals
		checkFile("wiki_02.html")

		// https://www.quora.com/Do-you-think-humanity-will-ever-walk-on-other-planets-outside-of-our-solar-system
		// Doubtful case
//		checkFile("quora_01.html")
	}


	private fun checkFile(fileName: String) {
		val element = processFile(fileName)!!
		val html = StringUtil.normaliseWhitespace(element.outerHtml())
		yes("$fileName: ${html.take(100)}", element.hasAttr("readability-result"))
	}

	private fun processFile(fileName: String): Element? {
		return Readability().process(parseTestDoc(fileName).children())
	}

	private fun parseTestDoc(fileName: String): Document {
		return Jsoup.parse(javaClass.classLoader.getResourceAsStream("test-docs/$fileName"), Charsets.UTF_8.toString(), "")
	}
}
