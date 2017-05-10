package azadev.toolset.readability.test

import azadev.toolset.readability.Readability
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.junit.*
import org.junit.Assert.assertEquals as eq
import org.junit.Assert.assertTrue as yes


class ReadabilityTest
{
	@Test fun basics() {
		checkFile("001.html")
		checkFile("002.html")

		checkFile("visitportugal_01.html")
		checkFile("visitportugal_02.html")

		checkFile("habrahabr_01.html")

		// Too many comments are here; Readability thinks it's the main content
//		checkFile("habrahabr_02.html")

		checkFile("meduza_01.html")


		checkFile("wiki_01.html")
		checkFile("wiki_02.html")

		// Doubtful case
//		checkFile("quora_01.html")
	}


	private fun checkFile(fileName: String) {
		val element = processFile(fileName)!!
		yes("$fileName: ${element.outerHtml().take(100)}", element.hasAttr("readability-result"))
	}

	private fun processFile(fileName: String): Element? {
		return Readability().process(Jsoup.parse(javaClass.classLoader.getResourceAsStream("test-docs/$fileName"), Charsets.UTF_8.toString(), "").children())
	}
}
