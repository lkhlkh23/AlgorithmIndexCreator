package domain;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.Stream;

public class SourceReader {
    /* 맥에서는 File.Seperator : /, Windows : \\ */
    private static final String DIRECTORY_PATH = "/Users/lee_ki_hyun/Desktop/CodeSquad/Algorithm/baekjoon";
    private static final String FILE_NAME = "Main.java";
    private static final String SPLIT_STANDARD = " : ";

    /* 디렉토리 전체 갯수 리턴 */
    public static long getQustionCount() throws IOException {
        return Files.list(Paths.get(DIRECTORY_PATH)).count();
    }

    /* 미해결 문제 갯수 리턴 */
    public static long getNotSolvedQuestionCount() throws IOException {
        return Files.list(Paths.get(DIRECTORY_PATH))
                .filter(path -> path.getFileName().toString().contains("Not"))
                .count();
    }

    private static List<String> getSourcePath() throws IOException {
        List<String> paths = new ArrayList<>();
        Files.list(Paths.get(DIRECTORY_PATH))
                .map(path -> path.getFileName().toString())
                .filter(path -> path.startsWith("Q"))
                .forEach(filePath -> paths.add(filePath));
        return paths;
    }

    private static String obtainFileName(String filePath) {
        return DIRECTORY_PATH + File.separator + filePath + File.separator + FILE_NAME;
    }

    private static Stream<String> obtainFileStream(String path, String keyword) throws IOException {
        return Files.lines(Paths.get(obtainFileName(path)), Charset.defaultCharset())
                    .filter(s -> s.contains(keyword))
                    .map(s -> s.split(SPLIT_STANDARD)[1].trim());

    }

    private static List<String> obtainQustionInfoList(String keyword, List<String> paths) throws IOException {
        List<String> qustionList = new ArrayList<>();
        for(String path : paths) {
            try {
                obtainFileStream(path, keyword)
                        .forEach(s -> qustionList.add(s));
            } catch (IOException e) {
                System.out.println(String.format("%s 폴더를 읽는 과정에서 오류 발생", path));

            }
        }

        return qustionList;
    }

    public static List<FileComponent> obtainFileComponents() throws IOException {
        List<String> questionNos = getSourcePath();
        List<String> qustionNames = obtainQustionInfoList("문제 : ", questionNos);
        List<String> qustionUrls = obtainQustionInfoList("url : ", questionNos);
        List<String> qustionRevieweds = obtainQustionInfoList("재풀이 : ", questionNos);

        List<FileComponent> fileComponents = new ArrayList<>();
        for(int i = 0; i < qustionNames.size(); i++) {
            String questionNo = questionNos.get(i);
            fileComponents.add(
                    new FileComponent(qustionUrls.get(i), qustionNames.get(i),
                            qustionRevieweds.get(i), !questionNo.contains("_"), questionNo));
        }

        return fileComponents;
    }

    public static void printFileComponet(List<FileComponent> fileComponents) {
        for(FileComponent fileComponent : fileComponents) {
            System.out.println(fileComponent.getQustionNo() + "\t" + fileComponent.getQustionName() + "\t" + fileComponent.getUrl()
            + "\t" + fileComponent.isComplete() + fileComponent.getReviewed());
        }
    }
}