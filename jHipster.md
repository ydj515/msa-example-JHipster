# jHipster

jHipster란 Spring Boot와 Angular 또는 React와 같은 최신 웹 프레임워크를 결합하여 웹 애플리케이션과 마이크로서비스를 생성하는 데 사용되는 개발 도구입니다.<br/>
주로 자바 기반 백엔드와 자바스크립트 기반 프론트엔드 개발을 빠르게 시작하고 관리할 수 있도록 도와줍니다.

## command

### 프로젝트 생성

```sh
jhipster
```

### entity 및 관련 코드 생성

1. command

```sh
jhipster entity {entity-name} # ex) jhipster entity bookCatalog
```

2. online editor
   [jdl-studio]에서 에디팅한 후 파일을 export하여 entity관련 코드 생성 가능 <br/>
   <img width="969" alt="image" src="https://github.com/user-attachments/assets/faa95766-8224-4580-a9fb-0dfbaa18b391">

```
jhipster import-jdl ./jhipster-jdl.jdl --force
```

```jdl
entity Rental {
	id Long,
    userId Long,
    retalStatus RentalStatus
}

entity RentedItem {
	id Long,
    bookId Long,
    rentedDate LocalDate,
    duDate LocalDate,
}

enum RentalStatus {
	RENT_AVAILABLE,
    RENT_UNAVAILABLE
}

relationship ManyToOne {
	RentedItem{rental} to Rental
}

paginate * with pagination

dto * with mapstruct

service all with serviceImpl

```

[//]: # "These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax"
[jHipster]: https://www.jhipster.tech/
[jdl-studio]: https://www.jhipster.tech/jdl-studio/
