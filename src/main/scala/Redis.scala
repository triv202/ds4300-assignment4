import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

class Redis() {
  private var emptyMessage = "ERROR: No such key found."
  private var data = Map[String, Any]();

  def findValueAtKey(key: String) : Any = {
    return data.getOrElse(key, emptyMessage)
  }

  def isEmptyValue(value: Any): Boolean = {
    return value.isInstanceOf[String] && value.equals(emptyMessage);
  };

  def isSingleString(value: Any): Boolean = {
    return value.isInstanceOf[String] && !value.equals(emptyMessage);
  };

  def isValidList(value: Any): Boolean = {
    return value.isInstanceOf[ListBuffer[String]];
  };

  def get(key: String): String = {
    val keyValue = findValueAtKey(key);
    if(!isValidList(keyValue)) {
      // Will return empty message when no key is found.
      return keyValue.asInstanceOf[String]
    }
    return "ERROR: Get can only handle String values";
  }

  def set(key: String, value: String): Unit = {
    val keyValue = findValueAtKey(key);
    if(isEmptyValue(keyValue)) {
      return data += (key -> value);
    }
    else return "Error: Key already taken by another value";
  };

  def lpush(key: String, value: String): Unit = {
    val keyValue = findValueAtKey(key);
    if(isSingleString(keyValue)) {
      return "ERROR: Value at key cannot be a string"
    } else if(isEmptyValue(keyValue)) {
      return data += (key -> ListBuffer[String](value))
    } else if(isValidList(keyValue)) {
      return keyValue.asInstanceOf[ListBuffer[String]].insert(0, value)
    }
  }

  def rpush(key: String, value: String): Unit = {
    val keyValue = findValueAtKey(key);
    if(isSingleString(keyValue)) {
      return "ERROR: Value at key cannot be a string"
    } else if(isEmptyValue(keyValue)) {
      return data += (key -> ListBuffer[String](value))
    } else if(isValidList(keyValue)) {
      val length = keyValue.asInstanceOf[ListBuffer[String]].size
      return keyValue.asInstanceOf[ListBuffer[String]].insert(length - 1, value)
    }
  }

  def lpop(key: String): String = {
    val keyValue = findValueAtKey(key);
    if(isValidList(keyValue)) {
      return keyValue.asInstanceOf[ListBuffer[String]](0);
    };
    else return "ERROR: Key does not contain a valid list";
  }

  def rpop(key: String): String = {
    val keyValue = findValueAtKey(key);
    if(isValidList(keyValue)) {
      val length = keyValue.asInstanceOf[ListBuffer[String]].size
      return keyValue.asInstanceOf[ListBuffer[String]](length - 1);
    };
    else return "ERROR: Key does not contain a valid list";
  }

  def lrange(key: String, start: Int, stop: Int): ListBuffer[String] = {
    val keyValue = findValueAtKey(key);
    if(isValidList(keyValue)) {
      val length = keyValue.asInstanceOf[ListBuffer[String]].size
      if(start > length) {
        return ListBuffer[String]();
      }
      else if(stop > length) {
        return ListBuffer[String](rpop(key));
      } else {
        return keyValue.asInstanceOf[ListBuffer[String]].slice(start, stop);
      };
    };
    else return ListBuffer[String]("ERROR: Key does not contain a valid list");
  }

  def llen(key: String): Any = {
    val keyValue = findValueAtKey(key);
    if(isSingleString(keyValue)) {
      return "Error: Key does not contain a list";
    } else if(isEmptyValue(keyValue)) {
      return keyValue;
    } else if(isValidList(keyValue)) {
      return keyValue.asInstanceOf[ListBuffer[String]].size;
    }
  }

  def flushall(): Unit = {
    data.clear();
  }
}
