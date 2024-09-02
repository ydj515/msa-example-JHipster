# msa-example-JHipster

[jHipster]를 이용한 msa example

## prerequisite
- nvm
- nodejs 20 (LTS 64-bit version을 반드시 설치)
- java 17 or 21
- jhipster
- kafka
- mongodb

## install

- install jhipster

```sh
npm install -g generator-jhipster
```

mac에서는 brew install 가능하다.

```sh
brew install --cask brooklyn --no-quarantine
```

- (optional) install yeoman

```sh
npm install -g yo
```

## start
```sh
docker compose -f ./gateway/src/main/docker/jhipster-registry.yml up
```