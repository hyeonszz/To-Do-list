# TodoApp Infrastructure (Ansible)

## Directory

```text
infra/ansible
├─ ansible.cfg
├─ requirements.yml
├─ inventories/lab/hosts.ini
├─ group_vars/all.yml
├─ step1_php_db.yml
├─ step2_add_web_haproxy.yml
├─ step3_add_db_haproxy.yml
├─ step4_add_db_redundancy.yml
├─ step5_add_nfs.yml
├─ step6_add_iscsi.yml
└─ step7_add_dns.yml
```

## Prerequisites

- Ansible controller: Linux/macOS or WSL
- Target OS: Ubuntu/Debian 계열 (현재 롤은 `apt` 기반)
- SSH key 접속 가능

필수 컬렉션:

```bash
ansible-galaxy collection install -r requirements.yml
```

## 0) 인벤토리 수정

`inventories/lab/hosts.ini`의 IP/SSH 사용자 정보를 실제 환경으로 변경하세요.

## 실행 순서

```bash
cd infra/ansible

# 1단계: Web + DB 직결
ansible-playbook step1_php_db.yml

# 2단계: Web 앞단 HAProxy
ansible-playbook step2_add_web_haproxy.yml

# 3단계: DB 2대 + DB HAProxy
ansible-playbook step3_add_db_haproxy.yml

# 4단계: Web LB + DB LB + DB 2중화 토폴로지
ansible-playbook step4_add_db_redundancy.yml

# 5단계: NFS 서버 추가 (Web root 공유)
ansible-playbook step5_add_nfs.yml

# 6단계: iSCSI 타겟 추가
ansible-playbook step6_add_iscsi.yml

# 7단계: DNS 서버 추가 (최종)
ansible-playbook step7_add_dns.yml
```

## 주요 변수

`group_vars/all.yml`:

- `db_user`, `db_password`, `db_name`
- `nfs_export_dir`, `nfs_clients_cidr`
- `iscsi_iqn`, `iscsi_backing_file`
- `dns_zone_name`

## 검증 포인트

- Step 1: `http://<web-ip>/index.php`에서 DB 연결 상태 확인
- Step 2: `http://<lbweb-ip>/index.php`로 접속 시 Web 노드 분산 확인
- Step 3: Web 앱의 DB endpoint를 `lbdb`로 바라보게 구성했는지 확인
- Step 5: Web1/Web2에서 같은 `index.php` 내용 확인 (NFS 공유)
- Step 7: DNS 서버에 `app.<zone>`, `db.<zone>` 레코드 생성 확인

## 참고

- iSCSI는 환경마다 디스크 장치명과 MySQL 데이터 이전 절차가 달라, 현재는 타겟/로그인 자동화 중심으로 구성했습니다.
- 운영용으로는 DB 복제, 방화벽, TLS, 백업, 장애조치 정책을 추가해야 합니다.

