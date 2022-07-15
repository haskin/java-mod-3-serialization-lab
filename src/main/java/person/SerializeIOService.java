package person;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SerializeIOService implements PersonIOService {
    @Override
    public void writePeopleList(String filePath, List<Person> people) {
        String folderPath = Paths.get("").toAbsolutePath().toString() + filePath;
        people.stream().forEach(person -> {
            try {
                serializePerson(folderPath, person);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<Person> readPeopleList(String filePath) {
        String folderPath = Paths.get("").toAbsolutePath().toString() + filePath;
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        List<Person> people = new ArrayList<>();
        return Arrays.stream(listOfFiles).map(this::deserializePerson).collect(Collectors.toList());
    }

    private void serializePerson(String folderPath, Person person) throws IOException {
        ObjectOutputStream personObjectStream = null;
        try {
            String fileName = folderPath + "/" + person.getFirstName() + ".dat";
            FileOutputStream personFile = new FileOutputStream(fileName);
            personObjectStream = new ObjectOutputStream(personFile);
            personObjectStream.writeObject(person);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (personObjectStream != null) {
                personObjectStream.flush();
                personObjectStream.close();
            }
        }
    }

    private Person deserializePerson(File personFile) {
        try( FileInputStream fileInputStream = new FileInputStream(personFile);
             ObjectInputStream personObjectStream =  new ObjectInputStream(fileInputStream) ) {
            return (Person) personObjectStream.readObject();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
