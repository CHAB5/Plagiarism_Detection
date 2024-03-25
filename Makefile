JFLAGS = -cp
JC = javac
JVM= java
SRC_DIR = $(PWD)
EXTENSION = .java
FILE1=
FILE2=

TARGET = S40195862_detector


all: $(TARGET)

$(TARGET):
	$(JC) $(TARGET)$(EXTENSION)
	$(JVM) $(TARGET) $(FILE1) $(FILE2)

run: $(TARGET)

clean: $(RM) $(TARGET)

testPlagiarism: $(TARGET)
	@echo "Testing plagiarism test cases in ../data/plagiarismXX"
	@for file in ../data/plagiarism*; do echo "Testing $$file"; $(JVM) $(TARGET) FILE1=$$file/1.txt FILE2=$$file/2.txt;done

testNonPlagiarism: $(TARGET)
	@echo "Testing non-plagiarism test cases in ../data/okayXX"
	@for file in ../data/okay*; do echo "Testing $$file"; $(JVM) $(TARGET) $$file/1.txt $$file/2.txt;done

test: testPlagiarism testNonPlagiarism