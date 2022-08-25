public class Page {
  private final String text;
  private Integer 

  Page(String text) {
    this.text = text;
  }

  @override
  public  String toString(){
    return this.text
  }
}


class Book{
  private final List<Page> pages;
  private final int pagenum;

  // Constructor
  Book(List<Page> pages) {
    // this.pages = pages;
    // this.pagenum = 0;
    // DRY: 
    // This will invoke the constructor below
    // If you have a more generic constructor, you can always use it to 
    // define your more specific constructors
    this(pages, 0)
  }

  Book(List<Page> pages, int pagenum) {
    this.pages = pages
    this.pagenum = pagenum

  }

  Book nextPage() {
    this.gotoPage(this.pages, pagenum + 1)
  }

  // Those classes that are in the same package, you can just access the methods 
  // Public is for methods outside the package to access the methods
  Book prevPage() {
    this.gotoPage(this.pages, pagenum - 1)
  }

  // How come this doesn't need "public or private keyword"
  // Implement this first as it is more general functionality. 
  // Once you implement this you can implement nextPage/previousPage easily
  // Return Book so we can do method chaining
  Book gotoPage(int pageNumber) {
    // Shouldn't this throw an error instead??
    if(pageNumber > this.pages.size() - 1 || pagenum < 0){return this}
    return new Book(this.pages, pagenum);
  }

  // This is a modifier, it does something
  // Every class in Java inherits from object
  // If you don't specify it is called overloading a method
  @override
  public String toString() {
    return "\n---" + this.pages.get(this.pagenum) + "\n---" this.pagenum
  }
}

// Tests
// new Book(List.of(new Page("one"), new Page("two").nextPage().gotoPage(1)))