package GradeManagement;

import java.util.Scanner;

//Class to represent a subject
class Subject {
	private String subjectCode;
	private String subjectName;

	public Subject(String subjectCode, String subjectName) {
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}
}

//Class to represent a student
class Student {
	private String studentName;
	private int studentID;

	public Student(String studentName, int studentID) {
		this.studentName = studentName;
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public int getStudentID() {
		return studentID;
	}
}


//main class of our system
public class GradeManagementSystem {
	private static final Scanner scanner = new Scanner(System.in);
	private static final int MAX_STUDENTS = 100;
	private static final int MAX_SUBJECTS = 5;
	private static Student[] students = new Student[MAX_STUDENTS];
	private static Subject[] subjects = new Subject[MAX_SUBJECTS];
	private static double[][] grades = new double[MAX_STUDENTS][MAX_SUBJECTS];
	private static int studentCount = 0;
	private static int subjectCount = 0;

	public static void main(String[] args) {
		int choice;

		// Main menu loop
		do {
			System.out.println("\nGrade Management System Menu:\n");
			System.out.println("1. Add Student");
			System.out.println("2. Add Subject");
			System.out.println("3. Add Grade");
			System.out.println("4. Display Students and Grades");
			System.out.println("5. Exit");
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				addStudent();
				break;
			case 2:
				addSubject();
				break;
			case 3:
				addGrade();
				break;
			case 4:
				displayStudentsAndGrades();
				break;
			case 5:
				System.out.println("Exiting Grade Management System. Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		} while (choice != 5);
	}

	// Method to add a new student
	private static void addStudent() {
		try {
			if (studentCount < MAX_STUDENTS) {
				System.out.print("Enter student name: ");
				String studentName = scanner.next();
				System.out.print("Enter student ID: ");
				int studentID = scanner.nextInt();

				students[studentCount] = new Student(studentName, studentID);
				System.out.println("Student added successfully.");
				studentCount++;
			} else {
				System.out.println("Maximum number of students reached. Cannot add more.");
			}
		}
		// Handle invalid input for student ID
		catch (java.util.InputMismatchException e) {
			System.out.println("Invalid input. Please enter a valid integer for student ID.");
			scanner.nextLine(); // Clear the buffer
		}
	}

	// Method to add a new subject
	private static void addSubject() {
		if (subjectCount < MAX_SUBJECTS) {
			System.out.print("Enter subject code: ");
			String subjectCode = scanner.next();
			System.out.print("Enter subject name: ");
			String subjectName = scanner.next();

			subjects[subjectCount] = new Subject(subjectCode, subjectName);
			System.out.println("Subject added successfully.");
			subjectCount++;
		} else {
			System.out.println("Maximum number of subjects reached. Cannot add more.");
		}
	}

	// Method to add a grade for a student in a subject
	private static void addGrade() {
		try {
			if (studentCount > 0 && subjectCount > 0) {
				System.out.print("Enter student ID: ");
				int studentID = scanner.nextInt();
				int studentIndex = findStudentIndex(studentID);

				if (studentIndex != -1) {
					System.out.print("Enter subject code: ");
					String subjectCode = scanner.next();
					int subjectIndex = findSubjectIndex(subjectCode);

					if (subjectIndex != -1) {
						System.out.print("Enter grade for " + students[studentIndex].getStudentName() + " in "
								+ subjects[subjectIndex].getSubjectName() + ": ");
						double grade = scanner.nextDouble();

						if (grade < 0 || grade > 100) {
							// Throw exception for invalid grade
							throw new IllegalArgumentException(
									"Invalid grade. Please enter a grade between 0 and 100.");
						}

						grades[studentIndex][subjectIndex] = grade;
						System.out.println("Grade added successfully.");
					} else {
						System.out.println("Subject not found. Please add the subject first.");
					}
				} else {
					System.out.println("Student not found. Please add the student first.");
				}
			} else {
				System.out.println("No students or subjects found. Add students and subjects first.");
			}
		} catch (java.util.InputMismatchException e) {
			// Handle invalid input exception for add grade
			System.out.println("Invalid input. Please enter a valid number for grade.");
			scanner.nextLine(); // Clear the buffer
		} catch (IllegalArgumentException e) {
			// to show invaidinput custom message
			System.out.println(e.getMessage());
		}

	}

	// Method to display students along with their grades for each subject
	private static void displayStudentsAndGrades() {
		if (studentCount > 0 && subjectCount > 0) {
			System.out.println("\nStudents and Grades:");

			// Print header with subject names
			System.out.print("Student ID\tStudent Name\t");
			for (int j = 0; j < subjectCount; j++) {
				System.out.print(subjects[j].getSubjectName() + "\t");
			}
			System.out.println();

			// Print students and their grades
			for (int i = 0; i < studentCount; i++) {
				System.out.print(students[i].getStudentID() + "\t\t" + students[i].getStudentName() + "\t\t");

				for (int j = 0; j < subjectCount; j++) {
					System.out.print(grades[i][j] + "\t\t");
				}
				System.out.println();
			}
		} else {
			System.out.println("No students or subjects found. Add students and subjects first.");
		}
	}

	// Helper method to find the index of a student in the array
	private static int findStudentIndex(int studentID) {
		for (int i = 0; i < studentCount; i++) {
			if (students[i].getStudentID() == studentID) {
				return i;
			}
		}
		return -1;
	}

	// Helper method to find the index of a subject in the array
	private static int findSubjectIndex(String subjectCode) {
		for (int i = 0; i < subjectCount; i++) {
			if (subjects[i].getSubjectCode().equalsIgnoreCase(subjectCode)) {
				return i;
			}
		}
		return -1;
	}
}
