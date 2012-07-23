package com.textmagic.extractor;

import java.util.Collections;
import java.util.Iterator;

/**
 * @author cairne
 * @date 2012-4-1
 */
public abstract class AbstractExtractor implements Extractor {

    private Iterator<String> iterator;

    private Extractor previousExtractor;

    public abstract boolean hasNextThisText();

    public abstract String nextThisText();

    public abstract void init(String text);

    private static class ProxyIterator implements Iterator<String> {

        private AbstractExtractor textExtractor;

        /**
         * @param textExtractor
         */
        public ProxyIterator(AbstractExtractor textExtractor) {
            super();
            this.textExtractor = textExtractor;
        }

        /**
         * @return
         */
        @Override
        public boolean hasNext() {
            return textExtractor.hasNext();
        }

        /**
         * @return
         */
        @Override
        public String next() {
            return textExtractor.next();
        }

        /**
         * 
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();

        }

    }

    public Extractor getPreviousExtractor() {
        return previousExtractor;
    }

    public Extractor setPreviousExtractor(Extractor previousExtractor) {
        this.previousExtractor = previousExtractor;
        return this;
    }

    protected enum DummyIterator implements Iterator<String> {
        instance;

        /**
         * @return
         */
        @Override
        public boolean hasNext() {
            return false;
        }

        /**
         * @return
         */
        @Override
        public String next() {
            return null;
        }

        /**
         * 
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public Iterator<String> iteratorThisText(String text) {
        if (text == null) {
            return DummyIterator.instance;
        }
        init(text);
        return new ProxyIterator(this);
    }

    public boolean hasNext() {
        if (hasNextThisText()) {
            return true;
        }
        boolean hasNextThisText = false;
        while (!hasNextThisText && iterator.hasNext()) {
            init(iterator.next());
            hasNextThisText = hasNextThisText();
        }
        if (!hasNextThisText) {
            iterator = DummyIterator.instance;
        }
        return hasNextThisText;
    }

    public String next() {
        return nextThisText();
    }

    /**
     * @param iterator
     * @return
     */
    @Override
    public Iterator<String> extract(String text) {
        if (previousExtractor != null) {
            this.iterator = previousExtractor.extract(text);
        } else {
            iterator = Collections.singletonList(text).iterator();
        }
        if (iterator.hasNext()) {
            return iteratorThisText(iterator.next());
        }
        return DummyIterator.instance;
    }

}
