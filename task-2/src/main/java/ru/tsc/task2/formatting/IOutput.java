package ru.tsc.task2.formatting;

import ru.tsc.task2.entities.ResultRow;

import java.util.List;

public interface IOutput {
    String getFormattingText(List<ResultRow> arrayListResult, List<ResultRow> linkedListResult, List<ResultRow> hashMapListResult);
}