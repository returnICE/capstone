var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Exprss' });
});

module.exports = router;
/**
 * @swagger
 * tags:
 *   name: Auth
 *   description: 로그인 처리
 * definitions:
 *   Auth_request:
 *     type: object
 *     required:
 *       - user_id
 *       - password
 *     properties:
 *       user_id:
 *         type: string
 *         description: 아이디
 *       password:
 *         type: string
 *         description: 비밀번호
 *   Auth_response:
 *     type: object
 *     required:
 *       - status
 *     properties:
 *       status:
 *         type: string
 *         description: 로그인 성공 여부- error, success
 *       token:
 *         type: object
 *         description: 계정 정보
 *   Response_error:
 *     type: object
 *     required:
 *       - status
 *     properties:
 *       message:
 *         type: string
 *         description: 오류 사유
 *       status:
 *         type: integer
 *         description: response code
 */

/**
 * @swagger
 *  paths:
 *    /login:
 *      post:
 *        tags:
 *        - "Auth"
 *        summary: "Login process"
 *        description: ""
 *        consumes:
 *        - "application/json"
 *        produces:
 *        - "application/json"
 *        parameters:
 *        - in: "body"
 *          name: "body"
 *          description: "로그인 계정 정보와 서비스 정보를 전달"
 *          required: true
 *          schema:
 *            $ref: "#/definitions/Auth_request"
 *        responses:
 *          200:
 *            description: "로그인 결과"
 *            schema:
 *              $ref: "#/definitions/Auth_response"
 *          400:
 *            description: "잘못된 데이터"
 *            schema:
 *              $ref: "#/definitions/Response_error"
 *          500:
 *            description: "로그인 오류 & 실패"
 *            schema:
 *              $ref: "#/definitions/Response_error"
 */