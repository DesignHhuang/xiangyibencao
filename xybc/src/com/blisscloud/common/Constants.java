package com.blisscloud.common;

/**
 * @author Administrator ϵͳ����
 */
public class Constants {
	/**
	 * ���絯������
	 */
	public static final String POP_SCREEN_OF_NORMAL = "0"; // ��ͻ����絯����
	public static final String POP_SCREEN_OF_TRANSFER = "1"; // ��ͻ����絯��֮���ת��
	public static final String POP_SCREEN_OF_BILL_OUTBND = "2"; // �򹤵��Ⲧʱ�ĵ���
	public static final String POP_SCREEN_OF_AFTER_ADD_BILL = "3"; // ������¼ʱ�ĵ���
	public static final String POP_SCREEN_OF_CMP_CALL = "8"; // Compaign
																// OCS�Զ����ʱ�ĵ���
																// ���
	public static final String POP_SCREEN_OF_NO_CALL = "9"; // û������ʱ�ļٵ�����Ϊ�˹�����ɺ���ݲɼ�,�������־�

	public static final String CUR_CUSTOMER_ID = "CUR_CUSTOMER_ID"; // ��ǰ�Ŀͻ�ID
	public static final String CUR_CALL_ID = "CUR_CALL_ID"; // ��ǰ����ID
	public static final String CUR_VOICE_WORK_ID = "CUR_VOICE_WORK_ID"; // ��ǰ����ID

	public static final String CUR_CALL_TEL = "tel"; // �������
	public static final String CUR_CALL_TYPE = "CALL_TYPE"; // ��������
	public static final String CUR_AGENT_ID = "AgentId"; // ��ǰ��ϯID
	public static final String CUR_AGENT_OFFICE_TEL = "AgentOfficeTel"; // �û��칫�ҵ绰
	public static final String CUR_AGENT_HOME_TEL = "AgentHomeTel"; // ��ǰ�û���ͥ�绰
	public static final String CUR_AGENT_MOBILE = "AgentMobile"; // ��ǰ�û��ֻ����
	public static final String CUR_AGENT_ROLE_MAP = "AgentRoleMap"; // ��ǰ��ɫmap��
	public static final String CUR_AGENT_CENTER_FLAG = "centerFlag"; // ʡ��Ȩ��

	public static final String CUR_AGENT_NAME = "AgentName"; // ��ǰ��ϯID
	public static final String CUR_AGENT_LOCAL_NO = "LocalNo"; // �����
	public static final String CUR_ROLE_ID = "RoleId"; // ��ǰ��ɫID
	public static final String CUR_ROLES = "RoleStringList"; // ��ɫ�б?�ԡ������ֿ�
	public static final String SPLITE_CHAR = "/"; // �ַ�ָ��
	public static final String SPLITE_STRING = "=>"; // �ַ�ָ��

	public static final String PROC_END = "PROC_END"; // ������
	public static final String PROC_END_OK = "0"; // ������OK

	public static final String BOOLEAN_FALSE = "0"; // �ַ�false
	public static final String BOOLEAN_TRUE = "1"; // �ַ�true

	public static final String USE_FALSE = BOOLEAN_FALSE; // ������
	public static final String USE_TRUE = BOOLEAN_TRUE; // ����

	public static final String TRANSFER_CALL_IN = "2"; // ת�ӽ����־

	/**
	 * var CALLTYPE_UNKNOWN = 0; var CALLTYPE_INTERNAL = 1; var CALLTYPE_INBOUND
	 * = 2; var CALLTYPE_OUTBOUND = 3; var CALLTYPE_CONSULT = 4;
	 */
	public static final String CALL_UNKNOWN = "0";
	public static final String CALL_INTERNAL = "1"; // ת��
	public static final String CALL_IN = "2"; // ����
	public static final String CALL_OUT = "3"; // ����
	public static final String CALL_CONSULT = "4";

	public static String getCallTypeName(String _calltype) {
		if (_calltype == null) {
			return "";
		}
		_calltype = _calltype.trim();
		if (_calltype.equals(CALL_UNKNOWN)) {
			return "δ֪";
		}
		if (_calltype.equals(CALL_INTERNAL)) {
			return "ת��";
		}
		if (_calltype.equals(CALL_IN)) {
			return "����";
		}
		if (_calltype.equals(CALL_OUT)) {
			return "����";
		}
		if (_calltype.equals(CALL_CONSULT)) {
			return "��ѯ";
		}
		return "";

	}

	// �����ļ�����
	public static final String VOX_LINK_BY_BILL = "0"; // �򹤵����ʱ�Ĺ�����־λ
	public static final String VOX_LINK_BY_VOICE_WORD = "1"; // ���������ʱ�Ĺ�����־λ

	// strChannelId ��Դ����
	public static String CHANNEL_VOICE_TEL = "1"; // ����

	public static String CHANNEL_EMAIL = "2";

	public static String CHANNEL_CHAT = "3";

	public static String CHANNEL_VOICE_LEAVE_WORD = "4"; // ����
	//

	public static final String CALL_TYPE_ZI_XUN = "1";
	public static final String CALL_TYPE_TOU_SU = "2";
	public static final String CALL_TYPE_JU_BAO = "3";
	public static final String CALL_TYPE_JIAN_YI = "4";
	public static final String CALL_TYPE_YI_JIAN = "5";
	// Parameter name
	public static final String CALL_TEL = "CALL_TEL";
	public static final String CALL_TYPE = "CALL_TYPE";

	// CtiContents
	public static String VOICE_TEL = "1";
	public static String EMAIL = "2";
	public static String CHAT = "3";
	public static String VOICE_LEAVE_WORD = "4";

	static public String getChannelName(String _chnType) {
		if (_chnType == null) {
			return "�绰";
		}
		_chnType = _chnType.trim(); // ȥ�ո���
		//
		if (VOICE_TEL.equals(_chnType)) {
			return "�绰";
		} else if (EMAIL.equals(_chnType)) {
			return "�ʼ�";
		} else if (CHAT.equals(_chnType)) {
			return "����";
		} else if (VOICE_LEAVE_WORD.equals(_chnType)) {
			return "����";
		}
		return "�绰";
	}

	// �绰С�������Ч��־
	public static final String DM_SUM_STATE_USE = "1"; // ��Ч
	public static final String DM_SUM_STATE_NO_USE = "0"; // ��Ч
	public static final String DM_SUM_STATE_OTHER = "2"; // ROOT����20

}
