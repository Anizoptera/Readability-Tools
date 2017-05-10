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

		checkFile("100_visitportugal.html")
		checkFile("101_visitportugal.html")

		checkFile("200_habrahabr.html")

		// Too many comments are here; Readability thinks it's the main content
//		checkFile("201_habrahabr.html")

		checkFile("202_meduza.html")


		checkFile("300_wiki.html")
		checkFile("301_wiki.html")

		// Doubtful case
//		checkFile("400_quora.html")
	}


	private fun checkFile(fileName: String) {
		val element = processFile(fileName)!!
		yes("$fileName: ${element.outerHtml().take(100)}", element.hasAttr("readability-result"))
	}

	private fun processFile(fileName: String): Element? {
		return Readability().process(Jsoup.parse(javaClass.classLoader.getResourceAsStream("test-docs/$fileName"), Charsets.UTF_8.toString(), "").children())
	}
}
