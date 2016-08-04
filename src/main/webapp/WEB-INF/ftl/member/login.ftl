
<style>
	.form-signin .form-signin-heading,
	.form-signin .checkbox {
		margin-bottom: 10px;
	}
	.form-signin .checkbox {
		font-weight: normal;
		padding-left:20px;
	}
	.form-signin .form-control {
		height: auto;
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		padding: 10px;
		font-size: 16px;
	}
	.form-signin input[type="text"] {
		margin-bottom: -1px;
		border-bottom-right-radius: 0;
		border-bottom-left-radius: 0;
	}

	.form-signin input[type="password"] {
		margin-bottom: 10px;
		border-top-left-radius: 0;
		border-top-right-radius: 0;
	}
</style>

<div class="container">

	<div class="row">

		<div class="col-md-4 col-md-offset-4">
			<div id="message" style="display: none;">
				<div class="alert alert-danger" role="alert"></div>
			</div>

			<!-- 로그인 -->
			<div>
				<form class="form-signin" role="form" id="login">
					<input type="hidden" id="redirect_url" value="${redirectUrl?if_exists?html}" />
					<input type="hidden" id="chain" value="${chain?if_exists?html}" />
					<h2 class="form-signin-heading"><i class="fa fa-sign-in"></i> 로그인</h2>
					<input type="text" class="form-control" placeholder="아이디" id="user_id" name="user_id" required>
					<input type="password" class="form-control" placeholder="비밀번호" id="password" name="password" required>
					<div class="checkbox">
						<label>
							<input type="checkbox" name="remember_me"> Remember me
						</label>
					</div>
					<button class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
				</form>
			</div>

		</div>

	</div>

</div>

<script type="text/javascript">
	$('#login').submit(function(event) {
		event.preventDefault();

		$.ajax({
			url : '<@spring.url "/member/login" />',
			data: $('#login input').serialize(),
			type: 'POST',
			dataType : 'json',
			beforeSend: function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
			}
		}).done(function(responseData) {
			if (responseData.error) {
				$('#message div').text(responseData.message);
				$('#message').show(0, function() {
					$(this).delay(1000).hide(0);
				});
			} else {
				var redirectUrl = responseData.data;
				var chain = $("#chain").val();
				if (chain === 'true') {
					location.reload();
				} else {
					location.href = redirectUrl;
				}

			}
		});
	});
</script>
