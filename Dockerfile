FROM eclipse-temurin:21-jre

WORKDIR /app

RUN groupadd --system appgroup \
    && useradd --system --gid appgroup appuser \
    && mkdir -p /app/uploads/avatars \
    && chown -R appuser:appgroup /app

COPY --chown=appuser:appgroup target/*.jar app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]