package com.winners.libraryproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.winners.libraryproject.entity.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {
    @NotNull(message = "Please provide your firstName")
    @Size(min = 2, max = 80, message = "BookName '${validatedValue}' must be between {min} and {max} chracters long")
    @Column(length = 80, nullable = false)
    private String name;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{2}[- .]?\\d{5}[- .]?\\d{2}[- .]?\\d{1}$", message = "Please provide valid isbn number")
    @Column( name = "isbn", length = 17, nullable = false)
    private String isbn;


    @NotBlank(message = "Please provide not blank author id.")
    @NotNull(message = "Please provide your author id.")
    @Column(name = "author_id",nullable = false)
    private Long authorId;

    @NotBlank(message = "Please provide not blank publisher id.")
    @NotNull(message = "Please provide your publisher id.")
    @Column(name = "publisher_id",nullable = false)
    private Long publisherId;

    @NotBlank(message = "Please provide not blank category id.")
    @NotNull(message = "Please provide your category id.")
    @Column(name = "category_id",nullable = false)
    private Long categoryId;


    @NotBlank(message = "Please provide not blank loanable.")
    @NotNull(message = "Please provide your loanable.")
    @Column(name ="loanable", nullable = true)
    private Boolean loanable;

    @NotBlank(message = "Please provide not blank shelf code.")
    @NotNull(message = "Please provide your shelf code.")
    @Column(name = "shelf_code",length = 6,nullable = false)
    @Pattern(regexp = "^([A-Z\\-]{2})?\\d{3}$", message = "Please provide valid shelf code")
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
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name ="builtIn", nullable = false)
    private Boolean builtIn;

    public BookDTO(Book book){
        this.name = book.getName();
        this.isbn = book.getIsbn();
        this.authorId =book.getAuthor().getId();
        this.publisherId = book.getPublisher().getId();
        this.categoryId = book.getCategory().getId();
        this.loanable = book.getLoanable();
        this.shelfCode = book.getShelfCode();
        this.active = book.getActive();
        this.featured = book.getFeatured();
        this.createDate = book.getCreateDate();
        this.builtIn = book.getBuiltIn();
    }
}
