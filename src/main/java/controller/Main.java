package controller;

import domain.ExcelCreator;
import domain.FileComponent;
import domain.SourceReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<FileComponent> fileComponentList = SourceReader.obtainFileComponents();
        ExcelCreator.setSheetInfo();
        ExcelCreator.createSummary();
        ExcelCreator.createHeader();
        ExcelCreator.createExcelFile(fileComponentList);
        ExcelCreator.saveExcel();
    }
}
