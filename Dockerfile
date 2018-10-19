FROM clojure
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mv "$(lein uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" dispatch-standalone.jar
EXPOSE 8080
CMD ["java", "-jar", "dispatch-standalone.jar"]
