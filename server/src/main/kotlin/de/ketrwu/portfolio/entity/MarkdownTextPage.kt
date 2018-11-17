package de.ketrwu.portfolio.entity

data class MarkdownTextPage(
    var rendered: String = "",
    var meta: MarkdownMeta = MarkdownMeta(null)
) {
    fun getPath(): String {
        return "/page" + if (meta.tags.isEmpty()) {
            "/${meta.filename}"
        } else {
            "/${meta.tags.first()}/${meta.filename}"
        }
    }
}

open class MarkdownMeta(
    open var filename: String?,
    open var publishedAt: String = "",
    open var title: String = "",
    open var headline: String = "",
    open var description: String = "",
    open var tags: List<String> = listOf()
) {
    open fun of(yamlMeta: Map<String, List<String>>): MarkdownMeta {
        title = yamlMeta["title"]!![0]
        publishedAt = yamlMeta["publishedAt"]?.first() ?: "2018-01-01"
        headline = yamlMeta["headline"]!![0]
        description = yamlMeta["description"]!![0]
        tags = if (yamlMeta.containsKey("tags")) {
            yamlMeta["tags"]!!.map { it.toLowerCase() }
        } else {
            listOf()
        }
        return this
    }
}

class ProjectMarkdownMeta(
    override var filename: String?,
    override var publishedAt: String = "",
    override var title: String = "",
    override var headline: String = "",
    override var description: String = "",
    override var tags: List<String> = listOf(),
    var languages: List<String> = listOf()
) : MarkdownMeta(filename, publishedAt, title, headline, description, tags) {
    override fun of(yamlMeta: Map<String, List<String>>): ProjectMarkdownMeta {
        super.of(yamlMeta)
        languages = if (yamlMeta.containsKey("languages")) {
            yamlMeta["languages"]!!
        } else {
            listOf()
        }
        return this
    }
}