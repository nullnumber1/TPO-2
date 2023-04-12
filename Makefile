.PHONY: all clean test

all: test coverage

test:
	./gradlew cleanTest test
	open build/reports/tests/test/index.html

coverage:
	./gradlew jacocoTestReport
	open build/reports/jacoco/test/html/index.html

clean:
	./gradlew clean
	rm -rf build/test-results
	rm -rf build/reports
	rm -f src/main/resources/output/*.csv
	rm -rf src/main/resources/output/images/*.png
