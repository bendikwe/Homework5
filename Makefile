JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	TextFields.java \
	GVM.java \
	View.java \
	ControlPanel.java \
	App.java 

all: $(CLASSES:.java=.class)


clean:
	$(RM) *.class
