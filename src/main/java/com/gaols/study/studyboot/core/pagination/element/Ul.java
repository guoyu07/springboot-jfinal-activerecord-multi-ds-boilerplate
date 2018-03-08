package com.gaols.study.studyboot.core.pagination.element;

public class Ul extends Element {
    public Ul() {
        super("ul");
    }

    @Override
    public Element addChild(Element element) {
        if (!(element instanceof Li)) {
            throw new RuntimeException("Invalid child: " + element.getTagName());
        }

        return super.addChild(element);
    }
}
