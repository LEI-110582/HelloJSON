package iscte.ista;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class App {
    private static final String FIRSTNAMES_F_FILE = "json/firstnames_f.json";
    private static final String FIRSTNAMES_M_FILE = "json/firstnames_m.json";
    private static final String SURNAMES_FILE = "json/surnames.json";
    private static final String STUDENTS_CVS_FILE = "json/students.cvs";
    private static final int INITIAL_NUMBER = 21000;

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        Random random = new Random();

        try {
            // 1. Ler os ficheiros JSON
            List<String> firstnamesF = mapper.readValue(new File(FIRSTNAMES_F_FILE), new TypeReference<List<String>>() {});
            List<String> firstnamesM = mapper.readValue(new File(FIRSTNAMES_M_FILE), new TypeReference<List<String>>() {});
            List<String> surnames = mapper.readValue(new File(SURNAMES_FILE), new TypeReference<List<String>>() {});

            // Juntar todos os primeiros nomes
            List<String> allFirstNames = new ArrayList<>(firstnamesF);
            allFirstNames.addAll(firstnamesM);

            // 2. Pedir ao utilizador o número de alunos
            Scanner scanner = new Scanner(System.in);
            System.out.print("Indique o número de alunos a gerar: ");
            int totalStudents = scanner.nextInt();

            // Garantir nomes únicos
            Set<String> uniqueNames = new HashSet<>();
            List<Student> students = new ArrayList<>();
            int currentNumber = INITIAL_NUMBER;

            int maxPossibleUnique = allFirstNames.size() * surnames.size();
            if (totalStudents > maxPossibleUnique) {
                System.out.println("Aviso: Número pedido excede o total de combinações únicas possíveis (" + maxPossibleUnique + ").");
                totalStudents = maxPossibleUnique;
            }

            while (students.size() < totalStudents) {
                String firstName = allFirstNames.get(random.nextInt(allFirstNames.size()));
                String surname = surnames.get(random.nextInt(surnames.size()));
                String fullName = firstName + " " + surname;

                if (!uniqueNames.contains(fullName)) {
                    uniqueNames.add(fullName);
                    students.add(new Student(currentNumber++, fullName));
                }
            }

            // 3. Produzir o ficheiro CSV
            try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENTS_CVS_FILE))) {
                writer.println("Number,Name");
                for (Student student : students) {
                    writer.println(student.toCSV());
                }
            }

            System.out.println("Ficheiro '" + STUDENTS_CVS_FILE + "' gerado com sucesso com " + students.size() + " alunos!");

        } catch (IOException e) {
            System.err.println("Erro ao ler/escrever ficheiros: " + e.getMessage());
        }
    }
}