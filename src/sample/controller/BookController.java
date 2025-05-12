package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.dao.BookDao;
import sample.model.Book;

public class BookController {
    @FXML
    private ListView<Book> bookListView;
    @FXML
    private TextField titleField, authorField, genreField, publicationYearField, quantityAvailableField;
    @FXML
    private Button createButton, editButton, deleteButton, backButton;

    private BookDao bookDao = new BookDao();
    private ObservableList<Book> bookList;
    @FXML
    private void handleBack() {
        loadScene("main_view.fxml");
    }

    @FXML
    public void initialize() {
        createButton.setFont(Font.font("Arial", 14));
        backButton.setFont(Font.font("Arial", 14));
        editButton.setFont(Font.font("Arial", 14));
        deleteButton.setFont(Font.font("Arial", 14));
        titleField.setFont(Font.font("Arial", 14));
        authorField.setFont(Font.font("Arial", 14));
        genreField.setFont(Font.font("Arial", 14));
        publicationYearField.setFont(Font.font("Arial", 14));
        quantityAvailableField.setFont(Font.font("Arial", 14));
        bookListView.setCellFactory(lv -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.setFont(Font.font("Arial", 14));
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.getTitle()); // Áp dụng font
                }
            }
        });

        loadBooks();
        bookListView.setOnMouseClicked(event -> {
            Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                fillFields(selectedBook);
            }
        });
    }

    private void loadBooks() {
        bookList = FXCollections.observableArrayList(bookDao.getAllBooks());
        bookListView.setItems(bookList);
    }

    private void fillFields(Book book) {
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        genreField.setText(book.getGenre());
        publicationYearField.setText(String.valueOf(book.getPublicationYear()));
        quantityAvailableField.setText(String.valueOf(book.getQuantityAvailable()));
    }

    @FXML
    public void createBook() {
        Book newBook = new Book(0, titleField.getText(), authorField.getText(),
                genreField.getText(), Integer.parseInt(publicationYearField.getText()),
                Integer.parseInt(quantityAvailableField.getText()));
        bookDao.createBook(newBook);
        loadBooks();
    }

    @FXML
    public void editBook() {
        Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            selectedBook.setTitle(titleField.getText());
            selectedBook.setAuthor(authorField.getText());
            selectedBook.setGenre(genreField.getText());
            selectedBook.setPublicationYear(Integer.parseInt(publicationYearField.getText()));
            selectedBook.setQuantityAvailable(Integer.parseInt(quantityAvailableField.getText()));
            bookDao.updateBook(selectedBook);
            loadBooks();
        }
    }

    @FXML
    public void deleteBook() {
        Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookDao.deleteBook(selectedBook.getId());
            loadBooks();
        }
    }

    private void loadScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../" + fxml));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
