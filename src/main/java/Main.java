import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import logger.Logger;
import person.*;
import util.FileUtil;
import util.UserInput;

 /*
  TODO:
    x saves each person object to its own file, using the first name of the person as the file name,
    x then give the user the option to restore a person from their serialized copy
  */

public class Main {
    private static final Logger logger = Logger.getInstance();

    private static final String FILE_PATH = "/src/main/java/person.csv";
    private static final String JSON_FILE_PATH = "/src/main/java/person.json";

    private static final String SERIALIZE_FOLDER_PATH = "/src/main/java/serialize";


    private static List<Person> people = new ArrayList<>();

    public static void main(String[] args) {

        PrintService printService = new CsvPrintService();
        PersonIOService personIOService = new CsvIOService();
        String filePath = FILE_PATH;

        try (Scanner scanner = new Scanner(System.in)) {
            String fileFormat = UserInput.chooseFileFormat(scanner);
            if(fileFormat.equals("j")) {
                logger.log("JSON format will be used", "\n");
                printService = new JsonPrintService();
                personIOService = new JsonIOService();
                filePath = JSON_FILE_PATH;
            }
            else if(fileFormat.equals("s")) {
                logger.log("Serialize format will be used", "\n");
//                printService = new JsonPrintService();
                personIOService = new SerializeIOService();
                filePath = SERIALIZE_FOLDER_PATH;
            }
            else
                logger.log("CSV format will be used", "\n");

            boolean restoreFromList = UserInput.restoreFromList(scanner);

            if (restoreFromList) {
                logger.log("Restoring file.", "\n");
                if (!FileUtil.fileExists(filePath)) {
                    logger.log("File not found. Creating new file", "\n");
                    FileUtil.createNewFile(filePath);
                } else {
                    logger.log("Reading from file.", "\n");
                    people = personIOService.readPeopleList(filePath);
                }

            } else { // Do not restore file
                logger.log("File not restored. New file will be used.", "\n");
                if (FileUtil.fileExists(filePath)) {
                    // FileUtil.createNewFile(FILE_PATH);
                    logger.log("Previous file found. File will be deleted.", "\n");

                }
                FileUtil.deleteFile(filePath);
                FileUtil.createNewFile(filePath);
            }
            while (true) {
                String choice = UserInput.chooseOption(scanner);
                switch (choice) {
                    case "1":
                        logger.log("Adding a new person", "\n");
                        people.add(PersonIOService.getPerson(scanner));
                        break;
                    case "2":
                        logger.log("The current people in the list are:", "\n");
                        printService.print(people);
                        break;
                    case "3":
                        logger.log("Writing to file", "\n");
                        personIOService.writePeopleList(filePath, people);
                        logger.log("Exiting program");
                        return;
                    default:
                        return;
                }
            }

        } catch (Exception e) {

        }
    }

}
