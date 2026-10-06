# Notes API

API REST con autenticación por JWT. Cada usuario se registra, inicia sesión, y solo puede ver y crear sus propias notas — nunca las de otro usuario.

## Stack

Java 21, Spring Boot, Spring Security, JWT (jjwt), Spring Data JPA, PostgreSQL.

## Cómo funciona

1. El usuario se registra con email y contraseña. La contraseña nunca se guarda en texto plano — se guarda su hash (BCrypt).
2. Al iniciar sesión, si las credenciales son correctas, el servidor devuelve un JWT: un token firmado que representa esa sesión.
3. Para cualquier endpoint protegido, el cliente manda ese token en el header `Authorization: Bearer <token>`.
4. Un filtro revisa el token en cada petición, identifica quién es el usuario, y recién entonces deja pasar la petición al controller correspondiente.

La API es stateless: no guarda sesiones en el servidor, cada petición se autentica por sí sola con el token que trae.

## Cómo correrlo

Necesitás Docker instalado.

```bash
docker-compose up --build
```

Levanta la API y Postgres juntos. Las tablas se crean solas la primera vez. Queda en `http://localhost:8080`.

## Endpoints, con Postman

### 1. Registrarse
```
POST /auth/registrar
```
{ "email": "test@test.com", "password": "1234" }
```
`201 Created` si se creó, `409` si el email ya existe.

```
### 2. Iniciar sesión
```
POST /auth/login

Mismo body que el registro. La respuesta es el token, como texto plano (sin comillas ni `{}`) — copialo completo, lo vas a necesitar en los siguientes dos. `401` si las credenciales no coinciden.

### 3. Crear una nota
```
### 3. Crear una nota
```
POST /nota

Pestaña **Authorization** → tipo `Bearer Token` → pegá el token del paso 2.
Pestaña **Body** → `raw` → `JSON`:
```json
{ "titulo": "Primera nota", "contenido": "Probando desde Postman" }
```
`201 Created` con la nota recién creada.

### 4. Listar tus notas
```
GET /notas
```
Misma pestaña **Authorization** → `Bearer Token` → el mismo token. Devuelve solo las notas del usuario autenticado, nunca las de otros.

### Confirmar que la seguridad funciona

Repetí el paso 4 con la pestaña **Authorization** en `No Auth`, sin token. Debería devolver `401 Unauthorized` en vez de la lista — si te deja pasar igual, algo está mal configurado.

## Roles

Cada usuario se crea con rol `USER` por defecto. Hay rutas reservadas bajo `/admin/**` que solo un usuario con rol `ADMIN` puede usar — por ahora no implementé ningun endpoint que cambie el rol de un usuario después de ser creado (queda para más adelante).

## Estructura

```
controller/   endpoints REST
service/      lógica de negocio
repository/   acceso a datos (Spring Data JPA)
model/        entidades (Usuario, Nota)
dto/          request/response
exception/    excepciones propias y manejo centralizado
security/     JWT, filtro de autenticación, configuración de Spring Security
```

## Pendiente

- Tests (mockeando JWT y el contexto de autenticación)
- Deploy
- Endpoint para que un admin cambie el rol de otro usuario
