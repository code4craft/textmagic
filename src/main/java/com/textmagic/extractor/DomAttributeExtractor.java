package com.textmagic.extractor;

import java.util.Map;
import java.util.Map.Entry;

import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

/**
 * 
 * 这个类同时也可做String类型返回的extractor
 * 
 * @author cairne huangyihua@diandian.com
 * @date 2012-4-1
 */
public class DomAttributeExtractor extends AbstractExtractor {

    private String tag;

    private SimpleNodeIterator elements;

    private Map<String, String> attributes;

    /**
     * @param tag
     * @param attributes
     */
    public DomAttributeExtractor(String tag, Map<String, String> attributes) {
        super();
        this.tag = tag;
        this.attributes = attributes;
    }

    public String getTag() {
        return tag;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * @param text
     */
    @Override
    public void init(String text) {
        Parser parser = new Parser(new Lexer(text));
        TagNameFilter filter = new TagNameFilter(tag);
        try {
            NodeList nodeList = parser.parse(filter);
            for (Entry<String, String> entry : attributes.entrySet()) {
                nodeList.keepAllNodesThatMatch(new HasAttributeFilter(entry.getKey(), entry
                        .getValue()));
            }
            elements = nodeList.elements();
        } catch (ParserException e) {
            elements = null;
        }

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<" + tag);
        for (Entry<String, String> entry : attributes.entrySet()) {
            sb.append(" " + entry.getKey() + "=\"" + entry.getValue() + "\"");
        }
        sb.append(">");
        return sb.toString();
    }

    /**
     * @return
     */
    @Override
    public boolean hasNextThisText() {
        if (elements == null) {
            return false;
        }

        return elements.hasMoreNodes();
    }

    /**
     * @return
     */
    @Override
    public String nextThisText() {
        if (elements == null) {
            return null;
        }
        String html = elements.nextNode().toHtml();
        return html;
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        return tag.hashCode() + attributes.hashCode();
    }

    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DomAttributeExtractor)) {
            return false;
        }
        DomAttributeExtractor dom = (DomAttributeExtractor) obj;
        return dom.attributes.equals(this.attributes) && dom.tag.equals(this.tag);
    }
}
