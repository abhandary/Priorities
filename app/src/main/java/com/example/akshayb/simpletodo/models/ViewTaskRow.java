package com.example.akshayb.simpletodo.models;

/**
 * Created by akshayb on 2/7/17.
 */

public class ViewTaskRow {
    private String _label;
    private String _content;

    public ViewTaskRow(String label, String content) {
        _label = label;
        _content = content;
    }

    public void setLabel(String label) {
        _label = label;
    }

    public void setContent(String content) {
        _content = content;
    }

    public String getLabel() {
        return _label;
    }

    public String getContent() {
        return _content;
    }
}
