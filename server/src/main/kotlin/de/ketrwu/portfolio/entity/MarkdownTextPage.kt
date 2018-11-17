package de.ketrwu.portfolio.entity

data class MarkdownTextPage(
    var rendered: String = "",
    var meta: MarkdownMeta = MarkdownMeta()
)

data class MarkdownMeta(
    var filename: String = "",
    var title: String = "",
    var headline: String = "",
    var description: String = "",
    var tags: List<String> = listOf()
) {
    companion object {
        fun of(filename: String, yamlMeta: Map<String, List<String>>): MarkdownMeta {
            val title = yamlMeta["title"]!![0]
            val headline = yamlMeta["headline"]!![0]
            val description = yamlMeta["description"]!![0]
            val tags = if (yamlMeta.containsKey("tags")) {
                yamlMeta["tags"]!!.map { it.toLowerCase() }
            } else {
                listOf()
            }
            return MarkdownMeta(
                filename,
                title,
                headline,
                description,
                tags
            )
        }
    }
}