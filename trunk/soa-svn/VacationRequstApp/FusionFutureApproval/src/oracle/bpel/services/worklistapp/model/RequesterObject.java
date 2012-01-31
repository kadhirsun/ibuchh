package oracle.bpel.services.worklistapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.bpel.services.common.util.XMLUtil;
import oracle.bpel.services.workflow.client.IWorkflowServiceClient;
import oracle.bpel.services.workflow.client.IWorkflowServiceClientConstants;
import oracle.bpel.services.workflow.client.WorkflowServiceClientFactory;
import oracle.bpel.services.workflow.query.ITaskQueryService;
import oracle.bpel.services.workflow.query.model.ITaskSequence;
import oracle.bpel.services.workflow.query.model.TaskSequence;
import oracle.bpel.services.workflow.task.ITaskService;
import oracle.bpel.services.workflow.task.impl.TaskUtil;
import oracle.bpel.services.workflow.task.model.ObjectFactory;
import oracle.bpel.services.workflow.task.model.Task;
import oracle.bpel.services.workflow.verification.IWorkflowContext;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.soap.Body;
import org.apache.soap.Envelope;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.messaging.Message;
import org.apache.soap.transport.http.SOAPHTTPConnection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class RequesterObject {

  private static String TASK_NS = "http://xmlns.oracle.com/bpel/workflow/task";
  private String name;
  private String startDate = "";
  private String endDate = "";
  private String reason = "";


  private TreeModel treeModel = null;
  private RowKeySet rowSet = null;


  //Change following values based on your install
  private static final String REQUESTER_PASSWORD = "welcome1";
  private static final String ADMIN_USER = "weblogic";
  private static final String ADMIN_PASSWORD = "weblogic1";
  private static final String EJB_PROVIDER_URL =
    "t3://stanf03.us.oracle.com:7001";
  private static final String TASK_TARGET_NAMESPACE =
    "http://xmlns.oracle.com/VacationRequestApp/VacationRequest/VacationRequestTask";
  private static final String SOAP_ENDPOINT =
    "http://stanf03.us.oracle.com:7001/soa-infra/services/default/VacationRequest/vacationrequestprocess_client_ep";

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setStartDate(String date) {
    this.startDate = date;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setEndDate(String date) {
    this.endDate = date;
  }

  public String getEndDate() {
    return endDate;
  }


  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }


  public String submit() {

    try {
      System.out.println("submitting the request");

      SOAPHTTPConnection m_httpConnection = new SOAPHTTPConnection();
      Hashtable headers = m_httpConnection.getHeaders();
      if (headers != null) {
        headers.put("Content-Type", "text/xml");
      }

      SOAPMappingRegistry m_smr = new SOAPMappingRegistry();
      java.net.URL endpointURL = new java.net.URL(SOAP_ENDPOINT);
      Envelope requestEnv = new Envelope();
      Body requestBody = new Body();

      java.util.Vector requestBodyEntries = new java.util.Vector();
      requestBodyEntries.addElement(getInputPayload());
      requestBody.setBodyEntries(requestBodyEntries);
      requestEnv.setBody(requestBody);

      Message msg = new Message();
      msg.setSOAPTransport(m_httpConnection);
      System.out.println("SOAP Message:" + msg.toString());
      msg.send(endpointURL, "initiate", requestEnv);

      System.out.println("End of submitting the request");

      return "next";
    } catch (Exception exc) {
      // "Missing content type" error will always be thrown since initiate 
      // web service does not return any response back
      if (exc.getMessage().indexOf("Missing content type") == -1) {
        exc.printStackTrace(System.out);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null,
                           new FacesMessage(FacesMessage.SEVERITY_ERROR, exc.getMessage(),
                                            null));
        return "";
      } else {
        return "next";
      }
    }
  }

  public RowKeySet getRowKeySet() {
    return rowSet;
  }

  public void setRowKeySet(RowKeySet set) {
  }

  public String getApprovers() {
    try {
      System.out.println("Building the task object, and fetching its task sequence...........");

      IWorkflowContext ctx = null;
      ITaskService taskSvc = null;
      ITaskQueryService querySvc = null;

      System.out.println("Creating task query service client");
      Map<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String> properties =
        new HashMap<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String>();
      properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.MODE,
                     IWorkflowServiceClientConstants.MODE_DYNAMIC);
      properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_PROVIDER_URL,
                     EJB_PROVIDER_URL);
      properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_SECURITY_PRINCIPAL,
                     ADMIN_USER);
      properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_SECURITY_CREDENTIALS,
                     ADMIN_PASSWORD);

      IWorkflowServiceClient wfSvcClient =
        WorkflowServiceClientFactory.getWorkflowServiceClient(WorkflowServiceClientFactory.REMOTE_CLIENT,
                                                              properties,
                                                              null);
      taskSvc = wfSvcClient.getTaskService();
      querySvc = wfSvcClient.getTaskQueryService();

      System.out.println("Authenticating the user.....");
      ctx = querySvc.authenticate(name, REQUESTER_PASSWORD, "jazn.com", null);
      System.out.println("User authenticated");

      System.out.println("Creating the task object based on input data");
      Task task = new ObjectFactory().createTask();
      task.setTaskDefinitionId(TASK_TARGET_NAMESPACE);
      task.setCreator(name);
      Element inputPayload = getInputPayload();

      Document document = XMLUtil.createDocument();
      Element payloadElem = document.createElementNS(TASK_NS, "payload");
      Element cloneTemp = (Element)document.importNode(inputPayload, true);
      payloadElem.appendChild(cloneTemp);
      document.appendChild(payloadElem);

      task.setPayloadAsElement(payloadElem);
      System.out.println("Input Task object is " +
                         TaskUtil.getInstance().toString(task));

      List<ITaskQueryService.TaskSequenceType> sequenceTypes =
        new ArrayList<ITaskQueryService.TaskSequenceType>();
      sequenceTypes.add(ITaskQueryService.TaskSequenceType.ALL);

      List<ITaskQueryService.TaskSequenceBuilderContext> taskSequenceBuilderContext =
        new ArrayList<ITaskQueryService.TaskSequenceBuilderContext>();
      taskSequenceBuilderContext.add(ITaskQueryService.TaskSequenceBuilderContext.WORKFLOW_PATTERN);

      System.out.println("Fetching the task sequence for the task");
      TaskSequence taskSequence =
        querySvc.getTaskSequence(ctx, task, null, sequenceTypes,
                                 taskSequenceBuilderContext, true);

      System.out.println("Creating the tree model for the task sequence");

      setImage(taskSequence);

      List rootListNode = new ArrayList();
      rootListNode.addAll(taskSequence.getChildren());
      treeModel = new ChildPropertyTreeModel(rootListNode, "children");

      /*
            System.out.println("Setting tree mode to bean");
            FacesContext fc = FacesContext.getCurrentInstance();
            ExpressionFactory factory =
                fc.getApplication().getExpressionFactory();
            ValueExpression expr =
                factory.createValueExpression(fc.getELContext(),
                                              "treemodel.model",
                                              TreeModel.class);
            expr.setValue(fc.getELContext(), model);
            System.out.println("End of get approver");
            */
      return "next";

    } catch (Exception exc) {
      exc.printStackTrace();
      FacesContext context = FacesContext.getCurrentInstance();
      context.addMessage(null,
                         new FacesMessage(FacesMessage.SEVERITY_ERROR, exc.getMessage(),
                                          null));
      return "";
    }
  }

  public TreeModel getModel() {
    return treeModel;
  }

  public void setModel(TreeModel model) {

  }

  private Element getInputPayload() throws Exception {
    String elementString =
      "        <VacationRequestProcessRequest xmlns=\"http://xmlns.oracle.com/VacationRequest\">" +
      "            <creator>" + name + "</creator>" +
      "            <fromDate>" + startDate + "</fromDate>" +
      "            <toDate>" + endDate + "</toDate>" + "            <reason>" +
      reason + "</reason>" + "        </VacationRequestProcessRequest>";

    System.out.println("Input payload is " + elementString);
    Document document = XMLUtil.createDocument();
    Element temp =
      XMLUtil.parseDocumentFromXMLString(elementString).getDocumentElement();
    return temp;
  }

  private void setImage(ITaskSequence taskSequence) {
    ITaskSequence.Pattern pattern = taskSequence.getPattern();
    System.out.println(pattern);
    if (pattern == ITaskSequence.Pattern.ManagementChain) {
      taskSequence.setFlexString1("sequential.png");
    } else if (pattern == ITaskSequence.Pattern.Parallel) {
      taskSequence.setFlexString1("parallel.png");
    } else if (pattern == ITaskSequence.Pattern.Participant) {
      taskSequence.setFlexString1("user.png");
    } else if (pattern == ITaskSequence.Pattern.SequentialParticipant) {
      taskSequence.setFlexString1("sequential.png");
    } else {
      //taskSequence.setFlexString1("simple.png");
    }

    List children = taskSequence.getChildren();
    if (children != null) {
      for (int index = 0; index < children.size(); index++) {
        ITaskSequence taskSequenceChild = (ITaskSequence)children.get(index);
        setImage(taskSequenceChild);
      }
    }
  }
}

