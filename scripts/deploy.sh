#!/bin/bash

# 작업 디렉토리로 변경
cd /var/jenkins_home/custom/tuitui_chat || exit 1

# 환경변수
DOCKER_APP_NAME=tuitui-chat
LOG_FILE=./deploy.log

# 로그 기록 함수 포함
source ./log.sh

# 헬스 체크 함수 포함
source ./health_check.sh

# 메인 함수
main() {
    log "배포 시작일자 : $(date +'%Y-%m-%d %H:%M:%S')"

    # blue-green 배포 시도했으나 ec2 인스턴스 하나로 api, 채팅용 서버, redis를 가동하는건 무리가 가서 실패..ㅜ
    # git push 하면 자동 빌드되게 blue만 사용하자.
    ./deploy_blue.sh

    # EXIST_BLUE=$(docker-compose -p "${DOCKER_APP_NAME}-blue" -f docker-compose.blue.yml ps | grep -E "Up|running")

    # if [ -z "$EXIST_BLUE" ]; then
    #     ./deploy_blue.sh  # 블루 배포
    # else
    #     ./deploy_green.sh  # 그린 배포
    # fi

    # 사용하지 않는 이미지 삭제
    docker image prune -af

    log "배포 종료  : $(date +'%Y-%m-%d %H:%M:%S')"
    log "===================== 배포 완료 ====================="
}

# 스크립트 실행
main
