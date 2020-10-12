package StreamAPI.homework2;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George",
                "Samuel", "John", "Max", "Alex", "Kendra", "Sandra", "Monique", "Sarah");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown", "Johnson");
        List<Person> people = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 10_000_000; i++) {

            int age = random.nextInt(100);
            Sex sex = Sex.values()[new Random().nextInt(Sex.values().length)];
            Education education = processingAge(age, sex);

            people.add(new Person(names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    age, sex, education));
        }

        long count = people.stream()
                .filter(v -> v.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + count);

        List<String> militaryPeople = people.stream()
                .filter(v -> v.getAge() >= 18 && v.getAge() < 27)
                .map(v -> v.getSurname())
                .collect(Collectors.toList());
        System.out.println("Список фамилий призывников:");
        for (String militaryPerson : militaryPeople) {
            System.out.println(militaryPerson);
        }

        List<Person> workablePeople = people.stream()
                .filter(v -> v.getEducation().equals(Education.HIGHER))
                .sorted(Comparator.comparing(Person::getAge))
                .collect(Collectors.toList());
        System.out.println("Список потенциально работоспособных людей с высшим образованием: ");
        for (int i = 0; i < workablePeople.size(); i++) {
            System.out.println(workablePeople.get(i).toString());
        }
    }

    public static Education processingAge(int age, Sex sex) {
        if (age <= 14) {
            return Education.ELEMENTARY;
        } else if (age <= 18) {
            return Education.SECONDARY;
        } else if (age <= 22) {
            return Education.FURTHER;
        } else if (age <= 65 && sex.equals(Sex.MAN)) {
            return Education.HIGHER;
        } else if(age <= 60 && sex.equals(Sex.WOMEN)) {
            return Education.HIGHER;
        } else {
            return Education.RETIREE;
        }
    }
}
