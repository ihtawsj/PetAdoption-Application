import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

abstract class Pet {
    private static int nextId = 1;
    private int id;
    private String name;
    private String species;
    private String breed;
    private String vet;
    private boolean isVaccinated;
    private int price;

    public Pet(String name, String species, String breed, String vet, boolean isVaccinated, int price) {
        this.id = nextId++;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.vet = vet;
        this.isVaccinated = isVaccinated;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
    }

    public String getVet() {
        return vet;
    }

    public boolean isVaccinated() {
        return isVaccinated;
    }

    public int getPrice() {
        return price;
    }

    public void displayPetDetails(JTextArea textArea) {
        textArea.setText("ID: " + id + "\n" +
                         "Name: " + name + "\n" +
                         "Species: " + species + "\n" +
                         "Breed: " + breed + "\n" +
                         "Vet: " + vet + "\n" +
                         "Vaccinated: " + (isVaccinated ? "Yes" : "No") + "\n" +
                         "Price: $" + price);
    }
}

class Dog extends Pet {
    public Dog(String name, String breed, String vet, boolean isVaccinated, int price) {
        super(name, "Dog", breed, vet, isVaccinated, price);
    }
}

class Cat extends Pet {
    public Cat(String name, String breed, String vet, boolean isVaccinated, int price) {
        super(name, "Cat", breed, vet, isVaccinated, price);
    }
}

class Bird extends Pet {
    public Bird(String name, String breed, String vet, boolean isVaccinated, int price) {
        super(name, "Bird", breed, vet, isVaccinated, price);
    }
}

class Shelter {
    private String name;
    private List<Pet> petsAvailable;

    public Shelter(String name) {
        this.name = name;
        this.petsAvailable = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addPet(Pet pet) {
        petsAvailable.add(pet);
    }

    public void removePet(Pet pet) {
        petsAvailable.remove(pet);
    }

    public List<Pet> getPetsBySpecies(String species) {
        List<Pet> pets = new ArrayList<>();
        for (Pet pet : petsAvailable) {
            if (pet.getSpecies().equalsIgnoreCase(species)) {
                pets.add(pet);
            }
        }
        return pets;
    }

    public Pet getPetDetails(int id) {
        for (Pet pet : petsAvailable) {
            if (pet.getId() == id) {
                return pet;
            }
        }
        return null;
    }
}

public class PetAdoptionGUI {
    private static Shelter shelter = new Shelter("Happy Tails Shelter");
    private static JTextArea petDetailsArea = new JTextArea(10, 30);
    private static JComboBox<String> speciesComboBox = new JComboBox<>(new String[]{"Dog", "Cat", "Bird"});

    public static void main(String[] args) {
     
        shelter.addPet(new Dog("Buddy", "Golden Retriever", "Dr. Smith", true, 23000));
        shelter.addPet(new Dog("Max", "German Shepherd", "Dr. Johnson", false, 10000));
        shelter.addPet(new Cat("Whiskers", "Siamese", "Dr. Lee", true, 15000));
        shelter.addPet(new Cat("Fluffy", "Persian", "Dr. Patel", false, 12000));
        shelter.addPet(new Bird("Polly", "Parrot", "Dr. Garcia", true, 14000));

       
        JFrame frame = new JFrame("Pet Adoption System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

     
        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        
        JButton viewPetsButton = new JButton("View Available Pets");
        JButton addPetButton = new JButton("Add New Pet");
        JButton adoptPetButton = new JButton("Adopt a Pet");
        JButton exitButton = new JButton("Exit");

        topPanel.add(viewPetsButton);
        topPanel.add(addPetButton);
        topPanel.add(adoptPetButton);
        topPanel.add(exitButton);

        // Center panel with text area for pet details
        petDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(petDetailsArea);
        centerPanel.add(scrollPane);

        // Bottom panel for species selection
        bottomPanel.add(new JLabel("Choose Species:"));
        bottomPanel.add(speciesComboBox);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners
        viewPetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String species = (String) speciesComboBox.getSelectedItem();
                List<Pet> pets = shelter.getPetsBySpecies(species);
                petDetailsArea.setText("Available " + species + "s:\n");
                for (Pet pet : pets) {
                    petDetailsArea.append("ID: " + pet.getId() + " - " + pet.getName() + " - " + pet.getBreed() + " ($" + pet.getPrice() + ")\n");
                }
            }
        });

        addPetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nameField = new JTextField(10);
                JTextField breedField = new JTextField(10);
                JTextField vetField = new JTextField(10);
                JCheckBox vaccinatedCheckBox = new JCheckBox("Vaccinated");
                JTextField priceField = new JTextField(10);

                JPanel addPetPanel = new JPanel();
                addPetPanel.setLayout(new GridLayout(0, 2));
                addPetPanel.add(new JLabel("Name:"));
                addPetPanel.add(nameField);
                addPetPanel.add(new JLabel("Species:"));
                addPetPanel.add(speciesComboBox);
                addPetPanel.add(new JLabel("Breed:"));
                addPetPanel.add(breedField);
                addPetPanel.add(new JLabel("Vet:"));
                addPetPanel.add(vetField);
                addPetPanel.add(new JLabel("Is Vaccinated?"));
                addPetPanel.add(vaccinatedCheckBox);
                addPetPanel.add(new JLabel("Price:"));
                addPetPanel.add(priceField);

                int result = JOptionPane.showConfirmDialog(frame, addPetPanel, "Add New Pet", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    String species = (String) speciesComboBox.getSelectedItem();
                    String breed = breedField.getText();
                    String vet = vetField.getText();
                    boolean isVaccinated = vaccinatedCheckBox.isSelected();
                    int price = Integer.parseInt(priceField.getText());

                    Pet newPet;
                    switch (species.toLowerCase()) {
                        case "dog":
                            newPet = new Dog(name, breed, vet, isVaccinated, price);
                            break;
                        case "cat":
                            newPet = new Cat(name, breed, vet, isVaccinated, price);
                            break;
                        case "bird":
                            newPet = new Bird(name, breed, vet, isVaccinated, price);
                            break;
                        default:
                            JOptionPane.showMessageDialog(frame, "Invalid species", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                    }
                    shelter.addPet(newPet);
                    JOptionPane.showMessageDialog(frame, species + " added successfully");
                }
            }
        });

        adoptPetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField idField = new JTextField(10);
                JPanel adoptPanel = new JPanel();
                adoptPanel.add(new JLabel("Enter Pet ID to Adopt:"));
                adoptPanel.add(idField);

                int result = JOptionPane.showConfirmDialog(frame, adoptPanel, "Adopt Pet", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    int id = Integer.parseInt(idField.getText());
                    Pet selectedPet = shelter.getPetDetails(id);
                    if (selectedPet != null) {
                        JTextArea petInfo = new JTextArea(10, 30);
                        selectedPet.displayPetDetails(petInfo);
                        int confirm = JOptionPane.showConfirmDialog(frame, petInfo, "Confirm Adoption", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            shelter.removePet(selectedPet);
                            JOptionPane.showMessageDialog(frame, "Congratulations! You've adopted " + selectedPet.getName());
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Pet not found with ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Show the frame
        frame.setVisible(true);
    }
}
