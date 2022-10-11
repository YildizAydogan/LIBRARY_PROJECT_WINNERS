package com.winners.libraryproject.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //-------------------------------RELATIONS------------------------------------------------------------
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
            @JoinColumn(name = "author_id") })
    private Set<Author> authors = new HashSet<Author>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_categories", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
            @JoinColumn(name = "category_id") })
    private Set<Category> categories = new HashSet<Category>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "books_publishers", joinColumns = { @JoinColumn(name = "book_id") },
                                          inverseJoinColumns = {@JoinColumn(name = "publisher_id") })
    private Set<Publisher> publishers = new HashSet<>();

    //----------------------------------------------------------------------------------------------------

    @NotNull(message = "Please provide your firstName")
    @Size(min = 2, max = 80, message = "BookName '${validatedValue}' must be between {min} and {max} chracters long")
    @Column(length = 80, nullable = false)
    private String name;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{2}[- .]?\\d{5}[- .]?\\d{2}[- .]?\\d{1}$", message = "Please provide valid isbn number")
    @Column( name = "isbn", length = 17, nullable = false)
    private String isbn;

    @Column(name = "page_count",nullable = true)
    private int pageCount;

    @NotBlank(message = "Please provide not blank author id.")
    @NotNull(message = "Please provide your author id.")
    @Column(name = "author_id",nullable = false)
    private Long authorId;

    @NotBlank(message = "Please provide not blank publisher id.")
    @NotNull(message = "Please provide your publisher id.")
    @Column(name = "publisher_id",nullable = false)
    private Long publisherId;

    @Column(name = "publish_date",nullable = true)
    @JsonFormat(pattern="yyyy")
    private int publishDate;

    @NotBlank(message = "Please provide not blank category id.")
    @NotNull(message = "Please provide your category id.")
    @Column(name = "category_id",nullable = false)
    private Long categoryId;

    @Column(name = "image",nullable = true)
    private File image;

    @NotBlank(message = "Please provide not blank loanable.")
    @NotNull(message = "Please provide your loanable.")
    @Column(name ="loanable", nullable = true)
    private Boolean loanable;

    @NotBlank(message = "Please provide not blank shelf code.")
    @NotNull(message = "Please provide your shelf code.")
    @Column(name = "shelf_code",length = 6,nullable = false)
    @Pattern(regexp = "^([a-z\\-]{2})?\\d{3}$", message = "Please provide valid shelf code")
    private String shelfCode;


    @NotNull(message = "Please provide your book activeness.")
    @Column( name = "active", nullable = true)
    private Boolean active;

    @NotBlank(message = "Please provide not blank featured.")
    @NotNull(message = "Please provide your featured.")
    @Column(name = "featured", nullable = false)
    private Boolean featured;

    @Column(name = "create_date",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    @Column(name ="builtIn", nullable = false)
    private Boolean builtIn;

    public Book(String name, String isbn, int pageCount, int publishDate, File image, Boolean loanable, String shelfCode, Boolean active, Boolean featured, LocalDateTime createDate, Boolean builtIn) {
        this.name = name;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.publishDate = publishDate;
        this.image = image;
        this.loanable = loanable;
        this.shelfCode = shelfCode;
        this.active = active;
        this.featured = featured;
        this.createDate = createDate;
        this.builtIn = builtIn;
    }
}
