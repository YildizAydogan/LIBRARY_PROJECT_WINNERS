package com.winners.libraryproject.helper;

import com.winners.libraryproject.entity.Book;
import com.winners.libraryproject.entity.Loan;
import com.winners.libraryproject.entity.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelReportHelper {


    static String SHEET_USER="Users";
    static String[] USER_HEADERS= {"id","FirstName","LastName","BirthDate","Email","Address","Roles"};

    static String[] BOOK_HEADERS= {"id","Name","ISBN","PageCount","PublishDate","Author", "Category", "Publisher"};
    static String SHEET_BOOK="Books";

    static String SHEET_LOAN="Loans";
    static String[] LOAN_HEADERS= {"id","UserId","BookId","LoanDate","ExpireDate","ReturnDate","Notes"};



    //--------------------------USERS EXCEL LIST----------------------------------------------------------

    public static ByteArrayInputStream getUsersExcelReport(List<User> users) throws IOException {

        Workbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet(SHEET_USER);

        Row headerRow = sheet.createRow(0);

        for(int column=0; column<USER_HEADERS.length; column++) {
            Cell cell = headerRow.createCell(column);
            cell.setCellValue(USER_HEADERS[column]);
        }

        int rowId = 1;

        for(User user: users) {
            Row row = sheet.createRow(rowId++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getFirstName());
            row.createCell(2).setCellValue(user.getLastName());
            row.createCell(3).setCellValue(user.getPhone());
            row.createCell(4).setCellValue(user.getEmail());
            row.createCell(5).setCellValue(user.getAddress());
            row.createCell(7).setCellValue(user.getRoles().toString());
        }

        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());

    }
    //--------------------------BOOKS EXCEL LIST----------------------------------------------------------
    public static ByteArrayInputStream getBooksExcelReport(List<Book> books) throws IOException {
        Workbook workBook=new XSSFWorkbook();

        ByteArrayOutputStream out=new ByteArrayOutputStream();

        Sheet sheet=workBook.createSheet(SHEET_BOOK);

        Row headerRow= sheet.createRow(0);

        for(int column=0;column<BOOK_HEADERS.length;column++) {
            Cell cell=headerRow.createCell(column);
            cell.setCellValue(BOOK_HEADERS[column]);
        }

        int rowId=1;

        for(Book book: books) {
            Row row= sheet.createRow(rowId++);

            row.createCell(0).setCellValue(book.getId());
            row.createCell(1).setCellValue(book.getName());
            row.createCell(2).setCellValue(book.getIsbn());
            row.createCell(3).setCellValue(book.getPageCount());
            row.createCell(4).setCellValue(book.getPublishDate());
            row.createCell(5).setCellValue(book.getAuthorId());
            row.createCell(6).setCellValue(book.getCategoryId());
            row.createCell(7).setCellValue(book.getPublisherId());

        }

        workBook.write(out);

        workBook.close();

        return new ByteArrayInputStream(out.toByteArray());

    }


    //--------------------------LOANS EXCEL LIST----------------------------------------------------------
    public static ByteArrayInputStream getLoansExcelReport(List<Loan> loans) throws IOException {
        Workbook workBook=new XSSFWorkbook();

        ByteArrayOutputStream out=new ByteArrayOutputStream();

        Sheet sheet=workBook.createSheet(SHEET_LOAN);

        Row headerRow= sheet.createRow(0);

        for(int column=0;column<LOAN_HEADERS.length;column++) {
            Cell cell=headerRow.createCell(column);
            cell.setCellValue(LOAN_HEADERS[column]);
        }

        int rowId=1;



        for(Loan loan: loans) {
            Row row= sheet.createRow(rowId++);

            row.createCell(0).setCellValue(loan.getId());
            row.createCell(2).setCellValue(loan.getUserId().getId());
            row.createCell(4).setCellValue(loan.getUserId().getFirstName()+" "+loan.getUserId().getLastName());
            row.createCell(1).setCellValue(loan.getBookId()+ " " + loan.getBookId().getName());
            row.createCell(6).setCellValue(loan.getExpireDate().toString());
            row.createCell(7).setCellValue(loan.getLoanDate().toString());
            row.createCell(8).setCellValue(loan.getReturnDate().toString());

        }

        workBook.write(out);

        workBook.close();

        return new ByteArrayInputStream(out.toByteArray());

    }
}
